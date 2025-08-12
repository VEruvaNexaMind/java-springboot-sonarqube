package com.binpipe.spboot.demo;

import java.io.*;
import java.security.MessageDigest;

public class SecurityUtils {
    
    // Security issue: hardcoded salt
    private static final String SALT = "myFixedSalt123";
    
    // Weak cryptographic algorithm
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); // Weak algorithm
            md.update(SALT.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace(); // Security issue: stack trace exposure
            return null;
        }
    }
    
    // File handling security issue
    public void writeToFile(String filename, String content) {
        try {
            // No path validation - directory traversal possible
            FileWriter writer = new FileWriter("/tmp/" + filename);
            writer.write(content);
            writer.close(); // Should use try-with-resources
        } catch (IOException e) {
            // Silent failure - bad practice
        }
    }
    
    // Resource leak
    public String readFromFile(String filepath) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filepath);
            byte[] data = new byte[1024];
            fis.read(data);
            return new String(data);
        } catch (IOException e) {
            return "";
        }
        // Missing finally block - resource leak
    }
    
    // Inefficient algorithm - nested loops
    public boolean hasDuplicates(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] == array[j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
