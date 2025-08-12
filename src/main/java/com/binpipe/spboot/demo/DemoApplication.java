package com.binpipe.spboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.*;
import java.util.*;
import java.io.*;

@SpringBootApplication
@RestController
public class DemoApplication {

	// Security Issue: Hardcoded credentials
	private static final String DB_PASSWORD = "admin123";
	private static final String API_KEY = "sk-1234567890abcdef";
	
	// Unused variable
	private String unusedVariable = "This is never used";
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
		// Code duplication - same logic repeated
		String password = "admin123";
		if (password.equals("admin123")) {
			System.out.println("Login successful");
		}
		
		String pwd = "admin123";
		if (pwd.equals("admin123")) {
			System.out.println("Authentication successful");
		}
	}
	
	// Security Issue: SQL Injection vulnerability
	@GetMapping("/user")
	public String getUser(@RequestParam String userId) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Hardcoded connection string with credentials
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", DB_PASSWORD);
			stmt = conn.createStatement();
			
			// SQL Injection vulnerability - user input directly concatenated
			String sql = "SELECT * FROM users WHERE id = '" + userId + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				return "User found: " + rs.getString("name");
			}
		} catch (Exception e) {
			e.printStackTrace(); // Security issue: sensitive info in logs
		} finally {
			// Resource leak - connections not properly closed
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
		return "User not found";
	}
	
	// Code duplication - similar method
	@GetMapping("/admin")
	public String getAdmin(@RequestParam String adminId) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", DB_PASSWORD);
			stmt = conn.createStatement();
			
			// Same SQL injection pattern
			String sql = "SELECT * FROM admins WHERE id = '" + adminId + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				return "Admin found: " + rs.getString("name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
		return "Admin not found";
	}
	
	// Security Issue: Path traversal vulnerability
	@GetMapping("/file")
	public String readFile(@RequestParam String filename) {
		try {
			// Path traversal vulnerability
			File file = new File("/app/files/" + filename);
			Scanner scanner = new Scanner(file);
			StringBuilder content = new StringBuilder();
			while (scanner.hasNextLine()) {
				content.append(scanner.nextLine());
			}
			scanner.close();
			return content.toString();
		} catch (Exception e) {
			return "Error reading file";
		}
	}
	
	// Complexity and maintainability issues
	@GetMapping("/complex")
	public String complexMethod(@RequestParam String input) {
		// High cyclomatic complexity
		if (input != null) {
			if (input.length() > 0) {
				if (input.contains("admin")) {
					if (input.startsWith("super")) {
						if (input.endsWith("user")) {
							if (input.length() > 10) {
								if (input.contains("@")) {
									if (input.contains(".com")) {
										return "Super admin user with email";
									} else {
										return "Super admin user without email";
									}
								} else {
									return "Super admin user no @ symbol";
								}
							} else {
								return "Short super admin user";
							}
						} else {
							return "Super admin not ending with user";
						}
					} else {
						return "Admin not starting with super";
					}
				} else {
					return "Not an admin";
				}
			} else {
				return "Empty input";
			}
		} else {
			return "Null input";
		}
	}
	
	// Dead code - method never called
	private void deadMethod() {
		System.out.println("This method is never called");
		String deadVariable = "This is dead code";
	}
	
	// Poor exception handling
	@GetMapping("/unsafe")
	public String unsafeMethod() {
		try {
			int result = 10 / 0; // Division by zero
			return "Result: " + result;
		} catch (Exception e) {
			// Empty catch block - bad practice
		}
		return "Something went wrong";
	}
}
