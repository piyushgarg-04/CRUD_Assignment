package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/auth")

public class AuthController {
	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    @PostMapping("/signup")
	    public User signup(@RequestBody User user) {
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        return userRepository.save(user);
	    }

	    @PostMapping("/login")
	    public String login(@RequestBody User user) {
	        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
	        if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
	            return "Login successful!";
	        }
	        return "Invalid username or password!";
	    }
}
