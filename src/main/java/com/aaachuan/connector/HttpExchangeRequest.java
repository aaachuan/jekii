package com.aaachuan.connector;

import com.sun.net.httpserver.Headers;

import java.io.UnsupportedEncodingException;
import java.net.URI;

public interface HttpExchangeRequest {
    String getRequestMethod();

    URI getRequestURI();

    Headers getRequestHeaders();

    String getParameter(String name) throws UnsupportedEncodingException;
}
