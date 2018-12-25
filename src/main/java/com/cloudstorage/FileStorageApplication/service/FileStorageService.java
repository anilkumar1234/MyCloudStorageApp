package com.cloudstorage.FileStorageApplication.service;

import com.cloudstorage.FileStorageApplication.model.CloudFile;
import com.cloudstorage.FileStorageApplication.model.FileDownloadLink;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileStorageService {

    List<CloudFile> listFiles(String path) throws Exception;

    FileDownloadLink downloadFile(String path) throws Exception;

    CloudFile uploadFile(MultipartFile file, String path) throws Exception;

    File fetchResource(String cloudElementsLink, String fileName) throws Exception;
}
