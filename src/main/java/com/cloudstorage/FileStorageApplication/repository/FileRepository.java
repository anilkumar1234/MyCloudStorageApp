package com.cloudstorage.FileStorageApplication.repository;

import com.cloudstorage.FileStorageApplication.exception.AuthException;
import com.cloudstorage.FileStorageApplication.exception.FileStorageException;
import com.cloudstorage.FileStorageApplication.model.CloudFile;
import com.cloudstorage.FileStorageApplication.model.FileDownloadLink;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface FileRepository {

    List<CloudFile> listFiles(String path) throws FileNotFoundException, AuthException;

    FileDownloadLink getFile(String path) throws FileNotFoundException, AuthException;

    CloudFile upload(File file) throws FileStorageException, AuthException;
}
