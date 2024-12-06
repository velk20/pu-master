package org.fmi.rentacarextended.services;

import jakarta.validation.Valid;
import org.fmi.rentacarextended.dtos.LoginUserDTO;
import org.fmi.rentacarextended.exceptions.AuthenticationException;
import org.fmi.rentacarextended.exceptions.EntityNotFoundException;
import org.fmi.rentacarextended.models.User;
import org.fmi.rentacarextended.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        return this.userRepository
                .getUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id:" + id + " not found"));
    }

    public User getUserByUsername(String username) {
        return this.userRepository
                .getUserByUsername(username)
                .orElse(null);
    }


    public List<User> getAllUsers() {
        return this.userRepository.getAllUsers();
    }

    public boolean createUser(User user) {
        Optional<User> optionalUser = this.userRepository.getUserByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            throw new AuthenticationException("Username already exists");
        }

        return this.userRepository.createUser(user);
    }

    public boolean updateUser(int id, User user) {
        getUserById(id);

       return this.userRepository.updateUser(id, user);
    }

    public boolean deleteUser(int id) {
        getUserById(id);

       return this.userRepository.deleteUser(id);
    }

    public User getLatestUser() {
        return this.userRepository.getLatest().orElse(null);
    }

    public User login(LoginUserDTO user) {
        User userByUsername = this.getUserByUsername(user.getUsername());
        if (userByUsername == null || !userByUsername.getPassword().equals(user.getPassword())) {
            throw new AuthenticationException("Incorrect username or password");
        }
        else {
            return userByUsername;

        }

    }
}
