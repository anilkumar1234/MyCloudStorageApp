package com.cloudstorage.FileStorageApplication.exception;

import java.io.IOException;

public class FileStorageException extends Exception {
    public FileStorageException(String s, IOException ex) {
        super(s);
    }

    public FileStorageException(String s) {
        super(s);
    }
}
