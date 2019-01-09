package devlab.storage.localStorage.controllers;


import devlab.storage.localStorage.model.LocalFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("api/local/files/")
public class LocalFileController {

    private ServletContext servletContext;
    private String uploads;

    public LocalFileController(ServletContext servletContext) {
        this.servletContext = servletContext;
        createDirectory();
    }

    private void createDirectory() {
        uploads = servletContext.getRealPath("/uploads/");
        System.out.println(uploads);
        Path path = Paths.get(uploads);
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("list")
    public List<LocalFile> getResources() throws IOException {


            return Files.walk(Paths.get(uploads))
                    .filter(Files::isRegularFile)
                    .map(f -> {

                        try {
                            BasicFileAttributes bs = Files.readAttributes(f.toAbsolutePath(), BasicFileAttributes.class);

                            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/api/local/files/download/")
                                    .path(f.getFileName().toString())
                                    .toUriString();

                            String fileDeleteUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/api/local/files/delete/")
                                    .path(f.getFileName().toString())
                                    .toUriString();

                            return new LocalFile(
                                    f.getFileName().toString(),
                                    bs.creationTime().toString(),
                                    bs.lastModifiedTime().toString(),
                                    bs.size(),
                                    fileDownloadUri,
                                    fileDeleteUri,
                                    Files.probeContentType(f.toAbsolutePath()));

                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }).collect(Collectors.toList());
    }

    @PostMapping("upload")
    public void upload(@RequestParam("file") MultipartFile file) throws IOException {

        Path path = Paths.get(uploads + file.getOriginalFilename());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

    }


    @GetMapping(value = "/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletResponse response) throws IOException {
        //void*

        Path path = Paths.get(uploads + filename);
           Resource resource = new UrlResource(path.toUri());
          // byte[] content = Files.readAllBytes(path);


        //   MultipartFile targetFile = new MockMultipartFile(fileName,
        //           fileName, "text/plain", content);

            File targetFile = new File(uploads + filename);

         //  InputStreamResource resource = new InputStreamResource(new FileInputStream(targetFile));

        //  MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

        String contentType = Files.probeContentType(path);
        System.out.println(contentType);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + targetFile.getName() + "\"")
                .contentLength(targetFile.length())
                .body(resource);


        // ----------*
     //   response.setContentType(contentType);
     //   response.setHeader("Content-Disposition", "attachment;filename=" + filename);
     //   response.setStatus(HttpServletResponse.SC_OK);

     //   InputStream is = new FileInputStream(uploads + filename);
      //  FileCopyUtils.copy(is, response.getOutputStream());
      //  response.flushBuffer();

    }

    @DeleteMapping("delete/{file}")
    public void delete(@PathVariable("file") String fileName) {

        File file = new File(uploads + fileName);
        if (file.exists()) {
            file.delete();
        }
    }

}
