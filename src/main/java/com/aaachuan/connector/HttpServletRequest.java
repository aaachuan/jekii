package com.aaachuan.connector;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class HttpServletRequest implements HttpExchangeRequest{
    final HttpExchange httpExchange;

    public HttpServletRequest(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }

    @Override
    public String getRequestMethod() {
        return httpExchange.getRequestMethod();
    }

    @Override
    public URI getRequestURI() {
        return httpExchange.getRequestURI();
    }

    @Override
    public Headers getRequestHeaders() {
        return httpExchange.getRequestHeaders();
    }

    @Override
    public String getParameter(String name) throws UnsupportedEncodingException {
        String query = httpExchange.getRequestURI().getRawQuery();
        if (query != null) {
            Map<String, String> params = parseQuery(query);
            return params.get(name);
        }
        return null;
    }

    Map<String, String> parseQuery(String query) throws UnsupportedEncodingException {
        if (query == null || query.isEmpty()) {
            return Collections.emptyMap();
        }
        String[] ss = Pattern.compile("\\&").split(query);
        Map<String, String> map = new HashMap<>();
        for (String s : ss) {
            int n = s.indexOf('=');
            if (n >= 1) {
                String key = s.substring(0, n);
                String value = s.substring(n + 1);
                map.putIfAbsent(key, URLDecoder.decode(value, "UTF-8"));
            }
        }
        return map;
    }
}
