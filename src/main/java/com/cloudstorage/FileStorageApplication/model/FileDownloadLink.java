package com.cloudstorage.FileStorageApplication.model;

import com.cloudstorage.FileStorageApplication.utils.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.boot.jackson.JsonComponent;

import java.time.LocalDateTime;

@JsonComponent
public class FileDownloadLink {

    private String cloudElementsLink;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDateTime expires;
    private String providerLink;
    private String providerViewLink;

    public String getCloudElementsLink() {
        return cloudElementsLink;
    }

    public void setCloudElementsLink(String cloudElementsLink) {
        this.cloudElementsLink = cloudElementsLink;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public void setExpires(LocalDateTime expires) {
        this.expires = expires;
    }

    public String getProviderLink() {
        return providerLink;
    }

    public void setProviderLink(String providerLink) {
        this.providerLink = providerLink;
    }

    public String getProviderViewLink() {
        return providerViewLink;
    }

    public void setProviderViewLink(String providerViewLink) {
        this.providerViewLink = providerViewLink;
    }
}
