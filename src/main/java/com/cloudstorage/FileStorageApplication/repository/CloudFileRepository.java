package com.cloudstorage.FileStorageApplication.repository;

import com.cloudstorage.FileStorageApplication.config.CloudStorageConfig;
import com.cloudstorage.FileStorageApplication.exception.AuthException;
import com.cloudstorage.FileStorageApplication.exception.FileStorageException;
import com.cloudstorage.FileStorageApplication.model.CloudFile;
import com.cloudstorage.FileStorageApplication.model.FileDownloadLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class CloudFileRepository implements FileRepository {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CloudStorageConfig cloudConfig;
    
    private static final HttpEntity<String> httpEntity = null;

    @Override
    public List<CloudFile> listFiles(String path) throws FileNotFoundException, AuthException {
        String url=cloudConfig.getListContentsUrl();
        ResponseEntity<List> responseEntity=restTemplate.exchange(url, HttpMethod.GET,null,List.class,"path",path);
        List<CloudFile> result = responseEntity.getBody();
        return result;
    }

    @Override
    public FileDownloadLink getFile(String path) throws FileNotFoundException, AuthException {
        return null;
    }

    @Override
    public CloudFile upload(File file) throws FileStorageException, AuthException {
        return null;
    }
}
