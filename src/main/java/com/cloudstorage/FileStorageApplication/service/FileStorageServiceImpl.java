package com.cloudstorage.FileStorageApplication.service;

import com.cloudstorage.FileStorageApplication.exception.AuthException;
import com.cloudstorage.FileStorageApplication.model.CloudFile;
import com.cloudstorage.FileStorageApplication.model.FileDownloadLink;
import com.cloudstorage.FileStorageApplication.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    FileRepository fileRepository;

   // @HystrixCommand(fallbackMethod = "defaultContents")
    @Override
    public List<CloudFile> listFiles(String path) throws Exception {
        return fileRepository.listFiles(path);
    }

    public List<CloudFile> defaultContents(String path) throws FileNotFoundException, AuthException {
        return new ArrayList<>();
    }

    @Override
    public FileDownloadLink downloadFile(String path) throws Exception {
        FileDownloadLink lnk=fileRepository.getFile(path);
        return lnk;
    }

    @Override
    public CloudFile uploadFile(MultipartFile file, String path) throws Exception {
        CloudFile uploadedFile=fileRepository.upload(file,path);
        return uploadedFile;
    }

    @Override
    public File fetchResource(String cloudElementsLink, String fileName) throws Exception {
        return fileRepository.fetchFile(cloudElementsLink,fileName);
    }

}
