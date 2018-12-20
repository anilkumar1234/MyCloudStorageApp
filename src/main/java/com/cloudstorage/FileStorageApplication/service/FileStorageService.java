package com.cloudstorage.FileStorageApplication.service;

import com.cloudstorage.FileStorageApplication.exception.AuthException;
import com.cloudstorage.FileStorageApplication.exception.FileStorageException;
import com.cloudstorage.FileStorageApplication.model.CloudFile;
import com.cloudstorage.FileStorageApplication.model.FileDownloadLink;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileStorageService {

    List<CloudFile> listFiles(String path) throws FileNotFoundException, AuthException;

    FileDownloadLink downloadFile(String path) throws FileNotFoundException, AuthException;

    CloudFile uploadFile(MultipartFile file, String path) throws AuthException, FileStorageException, IOException;

    File fetchResource(String cloudElementsLink, String fileName) throws IOException,AuthException;
}
