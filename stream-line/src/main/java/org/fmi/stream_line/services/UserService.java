package org.fmi.stream_line.services;

import org.fmi.stream_line.entities.UserEntity;
import org.fmi.stream_line.exception.EntityNotFoundException;
import org.fmi.stream_line.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(()-> new EntityNotFoundException("User with username " + username + " not found"));
    }

    public UserEntity getById(String id) {
        return this.userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("User with id " + id + " not found"));
    }
}
