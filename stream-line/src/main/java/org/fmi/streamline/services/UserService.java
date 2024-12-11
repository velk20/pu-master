package org.fmi.streamline.services;

import org.fmi.streamline.entities.UserEntity;
import org.fmi.streamline.exception.EntityNotFoundException;
import org.fmi.streamline.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getByUsername(String username) {
        return this.userRepository.findByUsernameAndIsActiveTrue(username)
                .orElseThrow(()-> new EntityNotFoundException("User with username " + username + " not found"));
    }

    public UserEntity getById(String id) {
        return this.userRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(()-> new EntityNotFoundException("User with id " + id + " not found"));
    }
}
