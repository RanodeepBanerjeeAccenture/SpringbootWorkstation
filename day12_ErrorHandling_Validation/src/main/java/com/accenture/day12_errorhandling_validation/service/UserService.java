package com.accenture.day12_errorhandling_validation.service;

import com.accenture.day12_errorhandling_validation.model.UserModel;
import com.accenture.day12_errorhandling_validation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }
}
