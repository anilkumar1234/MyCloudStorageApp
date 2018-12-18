package com.cloudstorage.FileStorageApplication.service;

import com.cloudstorage.FileStorageApplication.exception.AuthException;
import com.cloudstorage.FileStorageApplication.exception.FileStorageException;
import com.cloudstorage.FileStorageApplication.model.CloudFile;
import com.cloudstorage.FileStorageApplication.model.FileDownloadLink;
import com.cloudstorage.FileStorageApplication.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    FileRepository fileRepository;

    @Override
    public List<CloudFile> listFiles(String path) throws FileNotFoundException, AuthException {
        return fileRepository.listFiles(path);
    }

    @Override
    public Resource downloadFile(String path) throws FileNotFoundException, AuthException {
        FileDownloadLink lnk=fileRepository.getFile(path);
        return null;
    }

    @Override
    public CloudFile uploadFile(File file) throws AuthException, FileStorageException {
        CloudFile uploadedFile=fileRepository.upload(file);
        return null;
    }

}
