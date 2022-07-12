package com.newBooking.domain.repository;

import com.newBooking.domain.entity.security.ERole;
import com.newBooking.domain.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
