package org.fmi.stream_line.repositories;


import org.fmi.stream_line.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String> {
    Optional<UserRoleEntity> findUserRoleByUserRole(String userRole);
}
