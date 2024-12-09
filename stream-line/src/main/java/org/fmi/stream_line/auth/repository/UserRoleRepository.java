package org.fmi.stream_line.auth.repository;


import org.fmi.stream_line.auth.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String> {
    Optional<UserRoleEntity> findUserRoleByUserRole(String userRole);
}
