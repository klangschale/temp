package com.example.encryption_decryption_app.encryption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EncryptionServer {
    public static void main(String[] args) {
        SpringApplication.run(EncryptionServer.class, args);
    }
}
