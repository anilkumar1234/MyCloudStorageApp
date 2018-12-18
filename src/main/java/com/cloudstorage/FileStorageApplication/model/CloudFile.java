package com.cloudstorage.FileStorageApplication.model;

import com.cloudstorage.FileStorageApplication.utils.CustomDateDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.time.LocalDate;
import java.util.List;

public class CloudFile {
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate createdDate;
    private boolean directory;
    private String id;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate modifiedDate;
    private String name;
    private String path;
    private long size;
    private List<String> tags;

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
