package com.cloudstorage.FileStorageApplication.handler;

import com.cloudstorage.FileStorageApplication.exception.AuthException;
import com.cloudstorage.FileStorageApplication.exception.ErrorDetails;
import com.cloudstorage.FileStorageApplication.exception.FileStorageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FileNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleFileNotFoundException(FileNotFoundException ex, WebRequest request){
        return null;
    }

    @ExceptionHandler(AuthException.class)
    public final ResponseEntity<ErrorDetails> handleFileNotFoundException(AuthException ex, WebRequest request){
        return null;
    }

    @ExceptionHandler(FileStorageException.class)
    public final ResponseEntity<ErrorDetails> handleFileNotFoundException(FileStorageException ex, WebRequest request){
        return null;
    }

}
