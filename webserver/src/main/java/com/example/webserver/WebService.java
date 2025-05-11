package com.example.webserver;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String process(String text, String action) {
        String url = "http://localhost:8080/" + action;
        ResponseEntity<String> response = restTemplate.postForEntity(url, text, String.class);
        return response.getBody();
    }
}
