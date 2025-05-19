package com.example.webserver;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class WebService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String process(String text, String action) {
        String url = "http://gateway:8080/" + action;

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        String paramName = action.equals("decrypt") ? "ciphertext" : "cleartext";
        map.add(paramName, text);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return response.getBody();
    }
}
