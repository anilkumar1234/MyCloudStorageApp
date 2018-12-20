package com.cloudstorage.FileStorageApplication.exception;

import java.time.LocalDateTime;

public class ErrorDetails {
    private String message;
    private String details;
    private int status;
    private LocalDateTime timestamp;

    public ErrorDetails(String message, String details, int status) {
        this.message = message;
        this.details = details;
        this.status = status;
        this.timestamp= LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
