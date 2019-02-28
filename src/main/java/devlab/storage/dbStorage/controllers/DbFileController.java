package devlab.storage.dbStorage.controllers;


import devlab.storage.commons.DateParser;

import devlab.storage.dbStorage.model.DbFile;
import devlab.storage.dbStorage.services.DbFileStorageService;
import devlab.storage.payload.UploadFileResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/db/files")
public class DbFileController {

    private static final Logger logger = LoggerFactory.getLogger(DbFileController.class);
    private DbFileStorageService dBFileStorageService;

    public DbFileController(DbFileStorageService dBFileStorageService) {
        this.dBFileStorageService = dBFileStorageService;
    }

    @PostMapping("/upload")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file)  {
        DbFile dbFile = dBFileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/db/files/download/")
                .path(dbFile.getId())
                .toUriString();

        String fileDeleteUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/db/files/delete/")
                .path(dbFile.getId())
                .toUriString();

        Date date = new Date();

//            return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri, fileDeleteUri,
//                    file.getContentType(), file.getSize(), date, DateParser.fromUtilDateToString(date, "dd/MM/yyyy HH:mm:ss" ));

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri, fileDeleteUri,
                file.getContentType(), file.getSize());

    }

    @GetMapping("/list")
    public ResponseEntity<List<UploadFileResponse>> getDBFiles() {

        List<UploadFileResponse> dbFiles = new ArrayList<>();

        dBFileStorageService.getAllDbFiles()
                .stream()
                .map(f -> {
                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/api/db/files/download/")
                            .path(f.getId())
                            .toUriString();

                    String fileDeleteUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/api/db/files/delete/")
                            .path(f.getId())
                            .toUriString();


//                        dbFiles.add(new UploadFileResponse(
//                                f.getFileName(),
//                                fileDownloadUri,
//                                fileDeleteUri,
//                                f.getFileType(),
//                                (long) f.getData().length,
//                                f.getDate(),
//                                DateParser.fromUtilDateToString(f.getDate(), "dd/MM/yyyy HH:mm:ss" )
//
//                        ));

                    dbFiles.add(new UploadFileResponse(
                            f.getFileName(),
                            fileDownloadUri,
                            fileDeleteUri,
                            f.getFileType(),
                            (long) f.getData().length


                    ));
                    return null;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(dbFiles, HttpStatus.OK);
    }

    @PostMapping("/uploadMultiple")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        DbFile dbFile = dBFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }


    // szybkie usuwanie pliku z linku w pliku JSON, bez tworzenia metody DELETE po stronie frontu.
    // możliwośc usuwania pliku bezpośrednio z tabeli na froncie przez <a href />

    @GetMapping("delete/{file}")
    public ResponseEntity<String> delete(@PathVariable String file) {
        return deleteFile(file);
    }

    // @DeleteMapping("delete/{file}")
    // public
    private ResponseEntity<String> deleteFile(@PathVariable String file) {
        dBFileStorageService.deleteFile(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

