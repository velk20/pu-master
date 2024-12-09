package org.fmi.stream_line.auth.repository;

import org.fmi.stream_line.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> getUserByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPhone(String phone);

}
