package com.example.encryption_decryption_app.decryption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DecryptionServer {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "decryption-server");
        SpringApplication.run(DecryptionServer.class, args);
    }
}

