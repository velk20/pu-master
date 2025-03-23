package com.fmi.master.service;

import com.fmi.master.model.User;
import com.fmi.master.repository.UserRepository;

public class RegisterService {
    private final UserRepository userRepository;
    private final static String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String register(final String username,
                           final String password,
                           final String password2,
                           final String email) {
        final String errorMessages = validateUserData(username, password, password2, email);
        if (!errorMessages.isBlank()) return errorMessages;

        saveUser(username, password, email);
        return "User successfully registered";
    }

    private void saveUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        userRepository.save(user);
    }

    private String validateUserData(String username, String password, String password2, String email) {
        StringBuilder builder = new StringBuilder();

        validateUsername(username, builder);
        validateEmail(email, builder);
        validatePassword(password, password2, builder);
        validateUsernameUniqueness(username, builder);
        validateUserEmailUniqueness(email, builder);
        return builder.toString();
    }

    private void validateUserEmailUniqueness(String email, StringBuilder builder) {
        User user = this.userRepository.findByEmail(email);
        if (user != null) {
            builder.append("User with this email already exists\n");
        }
    }

    private void validateUsernameUniqueness(String username, StringBuilder builder) {
        User user = this.userRepository.findByUsername(username);
        if (user != null) {
            builder.append("User with this username already exists\n");
        }
    }

    private static void validatePassword(String password, String password2, StringBuilder builder) {
        if (password == null || password.length() < 3 || password.length() > 20) {
            builder.append("Invalid password\n");
        }else {
            if (!password.equals(password2)) {
                builder.append("Passwords do not match\n");
            }
        }
    }

    private static void validateEmail(String email, StringBuilder builder) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            builder.append("Invalid email\n");
        }
    }

    private static void validateUsername(String username, StringBuilder builder) {
        if (username == null || username.length() < 3 || username.length() > 15) {
            builder.append("Invalid username\n");
        }
    }

}
