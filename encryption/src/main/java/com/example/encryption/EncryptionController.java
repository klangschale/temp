package com.example.encryption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EncryptionController {

    @Autowired
    private EncryptionService encryptionService;

    @GetMapping("/encrypt")
    public ResponseEntity<?> encrypt(@RequestParam String cleartext) {
        if (cleartext == null || cleartext.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Input text must not be empty.");
        }
        try {
            String ciphertext = encryptionService.encrypt(cleartext);
            return ResponseEntity.ok(ciphertext);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Encryption failed. Please try again.");
        }
    }

    @PostMapping("/encrypt")
    public ResponseEntity<?> encryptPost(@RequestParam String cleartext) {
        return encrypt(cleartext);
    }
}
