package org.fmi.streamline.repositories;

import org.fmi.streamline.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByIdAndIsActiveTrue(String id);
    Optional<UserEntity> findByUsernameAndIsActiveTrue(String username);
    Optional<UserEntity> findByEmailAndIsActiveTrue(String email);
    Optional<UserEntity> findByPhoneAndIsActiveTrue(String phone);

}
