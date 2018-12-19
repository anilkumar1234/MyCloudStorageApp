package com.cloudstorage.FileStorageApplication.service;

import com.cloudstorage.FileStorageApplication.exception.AuthException;
import com.cloudstorage.FileStorageApplication.exception.FileStorageException;
import com.cloudstorage.FileStorageApplication.model.CloudFile;
import com.cloudstorage.FileStorageApplication.model.FileDownloadLink;
import com.cloudstorage.FileStorageApplication.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    FileRepository fileRepository;

    @Override
    public List<CloudFile> listFiles(String path) throws FileNotFoundException, AuthException {
        return fileRepository.listFiles(path);
    }

    @Override
    public FileDownloadLink downloadFile(String path) throws FileNotFoundException, AuthException {
        FileDownloadLink lnk=fileRepository.getFile(path);
        return lnk;
    }

    @Override
    public CloudFile uploadFile(MultipartFile file) throws AuthException, FileStorageException {
        CloudFile uploadedFile=fileRepository.upload(file);
        return uploadedFile;
    }

    @Override
    public File fetchResource(String cloudElementsLink) throws IOException, AuthException {
        return fileRepository.fetchFile(cloudElementsLink);
    }


}
