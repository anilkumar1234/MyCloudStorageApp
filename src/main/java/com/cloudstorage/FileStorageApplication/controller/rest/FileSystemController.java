package com.cloudstorage.FileStorageApplication.controller.rest;

import com.cloudstorage.FileStorageApplication.model.CloudFile;
import com.cloudstorage.FileStorageApplication.model.FileDownloadLink;
import com.cloudstorage.FileStorageApplication.service.FileStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/rest")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value="CloudStorage",description = "operations on CloudStorage(DropBox) files",consumes = "application/json",produces = "application/json")
public class FileSystemController {

    private static final Logger logger= LoggerFactory.getLogger(FileSystemController.class);

    @Autowired
    FileStorageService fileStorageService;

    @ApiOperation(value="Get contents of a directory",response = List.class)
    @GetMapping("/list")
    public List<CloudFile> listFiles(@RequestParam(value="path",required = true)String path) throws Exception {
        logger.info("Path:"+path);
        return fileStorageService.listFiles(path);
    }

    @ApiOperation(value="Download a file from given path",response = ResponseEntity.class)
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam(value = "fileName",required = true) String fileName, HttpServletRequest request) throws Exception {
        FileDownloadLink lnk= fileStorageService.downloadFile(fileName);
        logger.info("File Url:"+lnk.getCloudElementsLink());
        File file=fileStorageService.fetchResource(lnk.getCloudElementsLink(),fileName);
        // Try to determine file's content type
        String contentType = null;
        contentType = request.getServletContext().getMimeType(file.getAbsolutePath());

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        Resource resource=new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }

    @ApiOperation(value="Upload a file to a directory",response = CloudFile.class)
    @PostMapping("/upload")
    public CloudFile uploadFile(@RequestParam(value="file",required = true) MultipartFile file,@RequestParam(value="path",required = true) String path) throws Exception {
        return fileStorageService.uploadFile(file,path);
    }

}
