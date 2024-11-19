package com.fmi.master.p1_rent_a_car.services;

import com.fmi.master.p1_rent_a_car.models.User;
import com.fmi.master.p1_rent_a_car.exceptions.UserNotFoundException;
import com.fmi.master.p1_rent_a_car.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        return this.userRepository
                .getUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id:" + id + " not found"));
    }

    public List<User> getAllUsers() {
        return this.userRepository.getAllUsers();
    }

    public boolean createUser(User user) {
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

}
