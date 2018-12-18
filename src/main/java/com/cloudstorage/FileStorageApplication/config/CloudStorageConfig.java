package com.cloudstorage.FileStorageApplication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="cloud.filesystem.api")
public class CloudStorageConfig {

    private String getContentsUri;

    private String usersecret;
    private String organization_secret;

    public String getListContentsUrl() {
    }


}
