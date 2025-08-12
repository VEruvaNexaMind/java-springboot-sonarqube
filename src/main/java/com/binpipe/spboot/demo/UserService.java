package com.binpipe.spboot.demo;

import java.util.*;

public class UserService {
    
    // Code duplication - same validation logic
    public boolean validateUser(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        if (username.length() < 3) {
            return false;
        }
        if (password.length() < 6) {
            return false;
        }
        return true;
    }
    
    // Duplicate validation logic
    public boolean validateAdmin(String adminName, String adminPassword) {
        if (adminName == null || adminName.trim().isEmpty()) {
            return false;
        }
        if (adminPassword == null || adminPassword.trim().isEmpty()) {
            return false;
        }
        if (adminName.length() < 3) {
            return false;
        }
        if (adminPassword.length() < 6) {
            return false;
        }
        return true;
    }
    
    // Security issue: weak random number generation
    public String generateToken() {
        Random random = new Random(); // Should use SecureRandom
        return "token_" + random.nextInt(10000);
    }
    
    // Performance issue: inefficient string concatenation
    public String buildMessage(List<String> messages) {
        String result = "";
        for (String message : messages) {
            result += message + ", "; // Should use StringBuilder
        }
        return result;
    }
    
    // Maintainability issue: magic numbers
    public boolean isValidAge(int age) {
        return age >= 18 && age <= 120; // Magic numbers
    }
    
    // Bug-prone: null pointer potential
    public String processUserName(String name) {
        return name.toUpperCase().trim(); // No null check
    }
}
