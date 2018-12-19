package com.cloudstorage.FileStorageApplication.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BeanConfig {

    private static final Logger logger= LoggerFactory.getLogger(BeanConfig.class);

    @Autowired
    CloudStorageConfig cloudStorageConfig;

    @Bean
    public RestTemplate getRestTemplate(){
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new CustomCloudInterceptor("Authorization", buildAuthString()));
        interceptors.add(new CustomCloudInterceptor("Accept", "application/json"));
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(interceptors);
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        return restTemplate;
    }

    public String buildAuthString(){
        String authString="User "+cloudStorageConfig.getUsersecret()+", Organization "+cloudStorageConfig.getOrganization_secret()
                +", Element "+cloudStorageConfig.getDropboxElement();
        logger.info("AuthString:"+authString);
        return authString;
    }
}
