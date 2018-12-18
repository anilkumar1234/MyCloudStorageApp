package com.cloudstorage.FileStorageApplication.service;

import com.cloudstorage.FileStorageApplication.exception.AuthException;
import com.cloudstorage.FileStorageApplication.exception.FileStorageException;
import com.cloudstorage.FileStorageApplication.model.CloudFile;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface FileStorageService {

    List<CloudFile> listFiles(String path) throws FileNotFoundException, AuthException;

    Resource downloadFile(String path) throws FileNotFoundException, AuthException;

    CloudFile uploadFile(File file) throws AuthException, FileStorageException;
}
