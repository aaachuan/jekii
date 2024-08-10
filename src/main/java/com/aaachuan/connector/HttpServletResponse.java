package com.aaachuan.connector;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HttpServletResponse implements HttpExchangeResponse{

    final HttpExchange httpExchange;

    public HttpServletResponse(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }

    @Override
    public Headers getResponseHeaders() {
        return httpExchange.getResponseHeaders();
    }

    @Override
    public void sendResponseHeaders(int returnCode) throws IOException {
        httpExchange.sendResponseHeaders(returnCode, 0);
    }

    @Override
    public OutputStream getResponseBody() {
        return httpExchange.getResponseBody();
    }
}
