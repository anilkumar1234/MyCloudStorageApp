package com.cloudstorage.FileStorageApplication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="cloud.filesystem.api")
public class CloudStorageConfig {

    private String getContentsUri;
    private String uploadFileUri;
    private String downloadFileUri;

    private String endPoint;

    private String usersecret;
    private String organization_secret;
    private String dropboxElement;

    public String getDropboxElement() {
        return dropboxElement;
    }

    public void setDropboxElement(String dropboxElement) {
        this.dropboxElement = dropboxElement;
    }

    public String getDownloadFileUri() {
        return endPoint+downloadFileUri;
    }

    public void setDownloadFileUri(String downloadFileUri) {
        this.downloadFileUri = downloadFileUri;
    }

    public String getListContentsUrl() {
        return endPoint+getContentsUri;
    }

    public String getUploadFileUri() {
        return endPoint+uploadFileUri;
    }

    public void setUploadFileUri(String uploadFileUri) {
        this.uploadFileUri = uploadFileUri;
    }

    public void setGetContentsUri(String getContentsUri) {
        this.getContentsUri = getContentsUri;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getUsersecret() {
        return usersecret;
    }

    public void setUsersecret(String usersecret) {
        this.usersecret = usersecret;
    }

    public String getOrganization_secret() {
        return organization_secret;
    }

    public void setOrganization_secret(String organization_secret) {
        this.organization_secret = organization_secret;
    }

}
