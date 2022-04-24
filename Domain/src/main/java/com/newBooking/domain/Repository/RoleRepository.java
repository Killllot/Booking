package com.newBooking.domain.Repository;

import com.newBooking.domain.Entity.Security.ERole;
import com.newBooking.domain.Entity.Security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
