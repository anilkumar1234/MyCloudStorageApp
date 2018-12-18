package com.cloudstorage.FileStorageApplication.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class CustomCloudInterceptor implements ClientHttpRequestInterceptor {
    private final String headerName;

    private final String headerValue;

    public CustomCloudInterceptor(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        httpRequest.getHeaders().set(headerName, headerValue);
        return clientHttpRequestExecution.execute(httpRequest, body);
    }
}
