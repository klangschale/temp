package com.example.encryption_decryption_app.decryption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
public class DecryptionController {

    @Autowired
    private DecryptionService decryptionService;

    @GetMapping("/decrypt")
    public ResponseEntity<?> decrypt(@RequestParam String ciphertext) {
        try {
            // URL decode special characters
            String decodedCiphertext = URLDecoder.decode(ciphertext, StandardCharsets.UTF_8.name()).replace(" ", "+");

            if (decodedCiphertext.isEmpty()) {
                return ResponseEntity.badRequest().body("Ciphertext must not be empty.");
            }

            String cleartext = decryptionService.decrypt(decodedCiphertext);
            return ResponseEntity.ok(cleartext);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Decryption failed: " + ex.getMessage());
        }
    }

    @PostMapping("/decrypt")
    public ResponseEntity<?> decryptPost(@RequestParam String ciphertext) {
        return decrypt(ciphertext);
    }
}

