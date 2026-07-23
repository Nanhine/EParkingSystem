package com.eparking.model.service;

import com.eparking.model.dto.LoginRequest;
import com.eparking.model.dto.LoginResponse;
import com.eparking.model.dto.SignupRequest;
import com.eparking.model.entity.User;
import com.eparking.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Long signup(SignupRequest req) {
        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("This email is already registered. Please log in instead.");
        }
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setPassword(encoder.encode(req.getPassword()));
        user = userRepo.save(user);
        return user.getUserId();
    }

    public Optional<LoginResponse> login(LoginRequest req) {
        return userRepo.findByEmail(req.getEmail())
                .filter(u -> encoder.matches(req.getPassword(), u.getPassword()))
                .map(u -> new LoginResponse(u.getUserId(), u.getName(), u.getEmail()));
    }
}