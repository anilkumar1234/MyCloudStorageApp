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
    public List<CloudFile> listFiles(String path) throws FileNotFoundException, AuthException {
        String url=cloudConfig.getListContentsUrl();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("path", path);
        logger.info("Url:"+builder.toUriString());
        ResponseEntity<List> responseEntity=restTemplate.exchange(builder.toUriString(), HttpMethod.GET,null,List.class);
        List<CloudFile> result = responseEntity.getBody();
        return result;
    }

    @Override
    public FileDownloadLink getFile(String path) throws FileNotFoundException, AuthException {
        String url=cloudConfig.getDownloadFileUri();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("path", path);
        logger.info("Url:"+builder.build(false).toUriString());
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity=new HttpEntity<>(httpHeaders);
        ResponseEntity<FileDownloadLink> responseEntity=restTemplate.exchange(builder.build(false).toUriString(), HttpMethod.GET,entity,FileDownloadLink.class);
        FileDownloadLink result = responseEntity.getBody();
        return result;
    }

    @Override
    public File fetchFile(String url, String fileName) throws AuthException, IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
        Path path=null;
        if (response.getStatusCode() == HttpStatus.OK) {
            String fname=fileName.trim().substring(fileName.lastIndexOf("/")+1);
            path=Files.write(Paths.get(fname), response.getBody());
        }else{
            throw new FileNotFoundException("File not found at the backend");
        }
        return path.getFileName().toFile();
    }

    @Override
    public CloudFile upload(MultipartFile file, String path) throws FileStorageException, AuthException, IOException {
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
        ResponseEntity<CloudFile> responseEntity=restTemplate.exchange(builder.toUriString(), HttpMethod.POST,requestEntity,CloudFile.class);
        return responseEntity.getBody();
    }
}
