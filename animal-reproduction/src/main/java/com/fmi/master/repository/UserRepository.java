package com.fmi.master.repository;

import com.fmi.master.model.User;

public interface UserRepository {
    void save(User user);
    User findByUsername(String username);
    User findByEmail(String email);
}
