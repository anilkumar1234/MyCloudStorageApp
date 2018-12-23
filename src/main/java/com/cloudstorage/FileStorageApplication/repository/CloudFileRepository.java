package com.cloudstorage.FileStorageApplication.repository;

import com.cloudstorage.FileStorageApplication.config.CloudStorageConfig;
import com.cloudstorage.FileStorageApplication.exception.AuthException;
import com.cloudstorage.FileStorageApplication.exception.FileStorageException;
import com.cloudstorage.FileStorageApplication.model.CloudFile;
import com.cloudstorage.FileStorageApplication.model.FileDownloadLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
@Repository
public class CloudFileRepository implements FileRepository {

    private static final Logger logger= LoggerFactory.getLogger(CloudFileRepository.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CloudStorageConfig cloudConfig;

    @Override
    public List<CloudFile> listFiles(String path) throws Exception {
        String url=cloudConfig.getListContentsUrl();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("path", path);
        logger.info("Url:"+builder.toUriString());
        ResponseEntity<List> responseEntity=null;
        try {
            responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,null,List.class);
        }catch (HttpClientErrorException err){
            if(err.getStatusCode()==HttpStatus.NOT_FOUND){
                throw new FileNotFoundException("File not found:"+path);
            }
            else if(responseEntity.getStatusCode()==HttpStatus.UNAUTHORIZED){
                throw new AuthException("Not authrized");
            }
        }catch (HttpServerErrorException err){
            throw new Exception("Internal Server occured");
        }

        List<CloudFile> result = responseEntity.getBody();
        return result;
    }

    @Override
    public FileDownloadLink getFile(String path) throws Exception {
        String url=cloudConfig.getDownloadFileUri();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("path", path);
        logger.info("Url:"+builder.build(false).toUriString());
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity=new HttpEntity<>(httpHeaders);
        ResponseEntity<FileDownloadLink> responseEntity=null;
        try {
            responseEntity = restTemplate.exchange(builder.build(false).toUriString(), HttpMethod.GET, entity, FileDownloadLink.class);
        }catch (HttpClientErrorException err){
            if(err.getStatusCode()==HttpStatus.NOT_FOUND){
                throw new FileNotFoundException("File not found:"+path);
            }
            else if(responseEntity.getStatusCode()==HttpStatus.UNAUTHORIZED){
                throw new AuthException("Not authrized");
            }
        }catch (HttpServerErrorException err){
            throw new Exception("Internal Server occured");
        }
        FileDownloadLink result = responseEntity.getBody();
        return result;
    }

    @Override
    public File fetchFile(String url, String fileName) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        Path path=null;
        ResponseEntity<byte[]> responseEntity=null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
        }catch (HttpClientErrorException err){
            if(err.getStatusCode()==HttpStatus.NOT_FOUND){
                throw new FileNotFoundException("File not found:"+path);
            }
            else if(responseEntity.getStatusCode()==HttpStatus.UNAUTHORIZED){
                throw new AuthException("Not authrized");
            }
        }catch (HttpServerErrorException err){
            throw new Exception("Internal Server occured");
        }

        return path.getFileName().toFile();
    }

    @Override
    public CloudFile upload(MultipartFile file, String path) throws Exception {
        String url=cloudConfig.getUploadFileUri();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("path", path);
        FileOutputStream fo= new FileOutputStream(file.getOriginalFilename());
        fo.write(file.getBytes());
        fo.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", new FileSystemResource(file.getOriginalFilename()));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parts, headers);
        logger.info("Url:"+builder.toUriString());

        ResponseEntity<CloudFile> responseEntity=null;
        try {
            responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST,requestEntity,CloudFile.class);
        }catch (HttpClientErrorException err){
            if(err.getStatusCode()==HttpStatus.NOT_FOUND){
                throw new FileNotFoundException("File not found:"+path);
            }
            else if(responseEntity.getStatusCode()==HttpStatus.UNAUTHORIZED){
                throw new AuthException("Not authrized");
            }
        }catch (HttpServerErrorException err){
            throw new Exception("Internal Server occured");
        }

        return responseEntity.getBody();
    }
}
