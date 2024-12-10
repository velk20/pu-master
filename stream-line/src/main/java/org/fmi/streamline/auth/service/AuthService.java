package org.fmi.streamline.auth.service;


import org.fmi.streamline.auth.models.AuthRequest;
import org.fmi.streamline.auth.models.AuthResponse;
import org.fmi.streamline.auth.models.RegisterDTO;
import org.fmi.streamline.entities.UserEntity;
import org.fmi.streamline.entities.UserRoleEntity;
import org.fmi.streamline.exception.EntityNotFoundException;
import org.fmi.streamline.repositories.UserRepository;
import org.fmi.streamline.repositories.UserRoleRepository;
import org.fmi.streamline.auth.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository, UserRoleRepository userRoleRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterDTO dto) {
        UserRoleEntity userRoleEntity = userRoleRepository
                .findUserRoleByUserRole(dto.getRole())
                .orElseThrow(() -> new EntityNotFoundException("No role like " + dto.getRole() + " was found!"));

        UserEntity user = UserEntity.builder()
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .age(dto.getAge())
                .phone(dto.getPhone())
                .createdAt(LocalDateTime.now())
                .lastModified(LocalDateTime.now())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .userRole(userRoleEntity)
                .isActive(true)
                .build();

        UserEntity saved = userRepository.save(user);
        var jwtToken = jwtUtil.generateToken(saved);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(()-> new EntityNotFoundException("User with username " + request.getUsername() + " not found!"));
        var jwtToken = jwtUtil.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
