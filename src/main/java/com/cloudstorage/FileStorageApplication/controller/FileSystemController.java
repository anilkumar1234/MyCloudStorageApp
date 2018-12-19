package com.cloudstorage.FileStorageApplication.controller;

import com.cloudstorage.FileStorageApplication.exception.AuthException;
import com.cloudstorage.FileStorageApplication.exception.FileStorageException;
import com.cloudstorage.FileStorageApplication.model.CloudFile;
import com.cloudstorage.FileStorageApplication.model.FileDownloadLink;
import com.cloudstorage.FileStorageApplication.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
public class FileSystemController {

    private static final Logger logger= LoggerFactory.getLogger(FileSystemController.class);

    @Autowired
    FileStorageService fileStorageService;

    @GetMapping("/list")
    public List<CloudFile> listFiles(@RequestParam("path")String path) throws FileNotFoundException, AuthException {
        logger.info("Path:"+path);
        return fileStorageService.listFiles(path);
    }

    @GetMapping("/download")
    public ResponseEntity<File> downloadFile(@RequestParam("fileName") String fileName, HttpServletRequest request) throws IOException, AuthException {
        FileDownloadLink lnk= fileStorageService.downloadFile(fileName);
        logger.info("Url:"+lnk.getCloudElementsLink());
        File file=fileStorageService.fetchResource(lnk.getCloudElementsLink());
        // Try to determine file's content type
        String contentType = null;
        contentType = request.getServletContext().getMimeType(file.getAbsolutePath());

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file);
    }

    @PostMapping("/upload")
    public CloudFile uploadFile(@RequestParam("file") MultipartFile file) throws AuthException, FileStorageException {
        return fileStorageService.uploadFile(file);
    }

}
