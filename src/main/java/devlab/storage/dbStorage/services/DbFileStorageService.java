package devlab.storage.dbStorage.services;

import devlab.storage.dbStorage.model.DbFile;
import devlab.storage.dbStorage.repositories.DbFileRepository;
import devlab.storage.exceptions.FileStorageException;
import devlab.storage.exceptions.MyFileNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@Service
public class DbFileStorageService {


    private DbFileRepository dbFileRepository;

    public DbFileStorageService(DbFileRepository dbFileRepository) {
        this.dbFileRepository = dbFileRepository;
    }

    public DbFile storeFile(MultipartFile file) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Date date = new Date(new java.util.Date().getTime());
            //  DbFile dbFile = new DbFile(fileName, file.getContentType(), file.getBytes());
            return dbFileRepository.save(new DbFile(fileName, file.getContentType(), file.getBytes(),date));

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DbFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

    public List<DbFile> getAllDbFiles() {
        return dbFileRepository.findAll();
    }

    public void deleteFile(String fileId) {
        dbFileRepository.deleteById(fileId);
    }


}
