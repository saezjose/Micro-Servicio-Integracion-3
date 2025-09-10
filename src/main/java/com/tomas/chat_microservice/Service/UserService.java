package com.tomas.chat_microservice.Service;

import com.tomas.chat_microservice.Model.User;
import com.tomas.chat_microservice.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String password) {
        String hashed = passwordEncoder.encode(password);
        User user = new User(username, hashed);
        return userRepository.save(user);
    }

    public boolean login(String username, String password) {
        return userRepository.findByUsername(username)
                .map(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElse(false);
    }
}
