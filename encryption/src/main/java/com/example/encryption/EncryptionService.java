package com.example.encryption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;

@Service
public class EncryptionService {
    private static final int IV_LENGTH = 16;
    private final String passphrase;
    private final String salt;
    private final SecureRandom secureRandom;

    public EncryptionService(
            @Value("${encryption.passphrase}") String passphrase,
            @Value("${encryption.salt}") String salt) {
        this.passphrase = passphrase;
        this.salt = salt;
        this.secureRandom = new SecureRandom();
    }

    public String encrypt(String cleartext) {
        try {
            // Generate a random IV
            byte[] iv = generateIV();
            
            SecretKey secretKey = getKeyFromPassphrase(passphrase);

            byte[] encrypted = encryptText(cleartext, secretKey, iv);
            
            byte[] combined = combineIVAndEncryptedText(iv, encrypted);
            
            // Convert to Base64 string
            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception ex) {
            throw new RuntimeException("Error encrypting", ex);
        }
    }

    // Generate a random initialization vector
    private byte[] generateIV() {
        byte[] iv = new byte[IV_LENGTH];
        secureRandom.nextBytes(iv);
        return iv;
    }

    // Encrypt text using AES
    private byte[] encryptText(String text, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        return cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
    }

    // Combine IV and encrypted text into one array
    private byte[] combineIVAndEncryptedText(byte[] iv, byte[] encrypted) {
        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
        return combined;
    }

    // Generate encryption key from passphrase
    private SecretKey getKeyFromPassphrase(String passphrase) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        PBEKeySpec spec = new PBEKeySpec(
            passphrase.toCharArray(),
            salt.getBytes(StandardCharsets.UTF_8),
            10000,  // iterations
            128
        );
        
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }
}
