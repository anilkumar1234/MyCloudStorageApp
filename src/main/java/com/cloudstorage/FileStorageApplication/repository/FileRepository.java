package com.cloudstorage.FileStorageApplication.repository;

import com.cloudstorage.FileStorageApplication.exception.AuthException;
import com.cloudstorage.FileStorageApplication.exception.FileStorageException;
import com.cloudstorage.FileStorageApplication.model.CloudFile;
import com.cloudstorage.FileStorageApplication.model.FileDownloadLink;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileRepository {

    List<CloudFile> listFiles(String path) throws FileNotFoundException, AuthException;

    FileDownloadLink getFile(String path) throws FileNotFoundException, AuthException;

    File fetchFile(String url, String fileName) throws AuthException, IOException;

    CloudFile upload(MultipartFile file, String path) throws FileStorageException, AuthException, IOException;
}
