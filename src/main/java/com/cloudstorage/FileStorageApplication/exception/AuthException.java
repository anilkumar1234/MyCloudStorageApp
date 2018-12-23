package com.cloudstorage.FileStorageApplication.exception;

public class AuthException extends Exception {

    public AuthException(String not_authrized) {
        super(not_authrized);
    }
}
