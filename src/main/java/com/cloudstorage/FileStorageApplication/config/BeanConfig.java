package com.cloudstorage.FileStorageApplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BeanConfig {

    @Autowired
    CloudStorageConfig cloudStorageConfig;

    @Bean
    public RestTemplate getRestTemplate(){
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new CustomCloudInterceptor("Authorization", buildAuthString()));
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    public String buildAuthString(){
        String authString="User "+cloudStorageConfig.getUsersecret()+", Organization "+cloudStorageConfig.getOrganization_secret();
        return authString;
    }
}
