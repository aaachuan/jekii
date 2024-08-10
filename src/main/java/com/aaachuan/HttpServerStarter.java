package com.aaachuan;

import com.aaachuan.config.HttpServerConfig;
import com.aaachuan.connector.HttpConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServerStarter {
    private static final Logger logger = LoggerFactory.getLogger(HttpServerStarter.class);

    public static void main(String[] args) {
        String host = "localhost";
        int port = 7777;
        HttpServerConfig config = new HttpServerConfig(host, port);

        try {
            HttpConnector httpConnector = new HttpConnector(config);
            httpConnector.bind();
            httpConnector.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
