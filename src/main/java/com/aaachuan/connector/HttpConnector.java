package com.aaachuan.connector;

import com.aaachuan.config.HttpServerConfig;
import com.sun.net.httpserver.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class HttpConnector implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpConnector.class);
    private HttpServerConfig httpServerConfig;
    private HttpServer httpServer;

    public HttpConnector(HttpServerConfig httpServerConfig) {
        this.httpServerConfig = httpServerConfig;
    }

    public void bind() throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(httpServerConfig.getHost(), httpServerConfig.getPort()),0);
    }

    public void start() throws IOException {
        httpServer.start();
        logger.info("start jekii http server at {}:{}", httpServerConfig.getHost(), httpServerConfig.getPort());
        HttpContext context = httpServer.createContext("/");
        context.setHandler(this::handle);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HttpServletRequest request = new HttpServletRequest(httpExchange);
        HttpServletResponse response = new HttpServletResponse(httpExchange);
        //process(request, response);
        simpleProcess(request,response);
    }

    void simpleProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String html = "<h1>hello, " + (name == null ? "world" : name) + ".</h1>";
        Headers responseHeaders = response.getResponseHeaders();
        responseHeaders.set("Content-type", "text/html; charset=utf-8");
        response.sendResponseHeaders(200);

        try(OutputStream out = response.getResponseBody()) {
            out.write(html.getBytes(StandardCharsets.UTF_8));
        }

    }

    void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求方法、URI、Path、Query
        String method = request.getRequestMethod();
        URI uri = request.getRequestURI();
        String path = uri.getPath();
        String query = uri.getRawQuery();
        logger.info("{}: {}?{}", method, path, query);

        //响应Header
        Headers responseHeaders = response.getResponseHeaders();
        responseHeaders.set("Content-type", "text/html; charset=utf-8");
        responseHeaders.set("Cache-Control", "no-cache");

        response.sendResponseHeaders(200);

        String s = "<h1>hello, world.</h1><p>" + LocalDateTime.now().withNano(0) + "</p>";
        try(OutputStream out = response.getResponseBody()) {
            out.write(s.getBytes(StandardCharsets.UTF_8));
        }
    }

    public void close() throws Exception {
        httpServer.stop(3);
    }
}
