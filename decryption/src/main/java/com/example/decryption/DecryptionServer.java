package com.example.decryption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DecryptionServer {
    public static void main(String[] args) {
        SpringApplication.run(DecryptionServer.class, args);
    }
}

