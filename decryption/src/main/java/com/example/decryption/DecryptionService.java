package com.example.encryption_decryption_app.decryption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class DecryptionService {
    private static final int IV_LENGTH = 16;
    private final String passphrase;
    private final String salt;

    public DecryptionService(
            @Value("${encryption.passphrase}") String passphrase,
            @Value("${encryption.salt}") String salt) {
        this.passphrase = passphrase;
        this.salt = salt;
    }

    public String decrypt(String base64Ciphertext) {
        try {
            // Decode Base64 to bytes
            byte[] combined = Base64.getDecoder().decode(base64Ciphertext);

            // Extract IV
            byte[] iv = new byte[IV_LENGTH];
            System.arraycopy(combined, 0, iv, 0, IV_LENGTH);

            // Extract encrypted text
            byte[] encrypted = new byte[combined.length - IV_LENGTH];
            System.arraycopy(combined, IV_LENGTH, encrypted, 0, encrypted.length);

            SecretKey secretKey = getKeyFromPassphrase(passphrase);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new RuntimeException("Error decrypting", ex);
        }
    }

    private SecretKey getKeyFromPassphrase(String passphrase) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(
                passphrase.toCharArray(),
                salt.getBytes(StandardCharsets.UTF_8),
                10000,
                128
        );
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }
}
