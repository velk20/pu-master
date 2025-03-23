package com.fmi.master.service;

import com.fmi.master.model.User;
import com.fmi.master.repository.UserRepository;

public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String loginUser(String username, String password) {
        final String errorMessages = validateUserLoginData(username, password);
        if (!errorMessages.isBlank()) return errorMessages;

        return "Login Successful";
    }

    private String validateUserLoginData(String username, String password) {
        StringBuilder stringBuilder = new StringBuilder();

        validateUsername(username, stringBuilder);
        validatePassword(password, stringBuilder);
        validateUsernameExisting(username, stringBuilder);

        return stringBuilder.toString();
    }

    private void validateUsernameExisting(String username, StringBuilder builder) {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            builder.append("User with this username doesn't exists\n");
        }
    }

    private static void validateUsername(String username, StringBuilder builder) {
        if (username == null || username.length() < 3 || username.length() > 15) {
            builder.append("Invalid username\n");
        }
    }

    private static void validatePassword(String password, StringBuilder builder) {
        if (password == null || password.length() < 3 || password.length() > 20) {
            builder.append("Invalid password\n");
        }
    }
}
