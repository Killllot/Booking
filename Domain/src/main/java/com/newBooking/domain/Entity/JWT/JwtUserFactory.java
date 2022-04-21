package com.newBooking.domain.Entity.JWT;

import com.newBooking.domain.Entity.Role;
import com.newBooking.domain.Entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    public JwtUserFactory() {

    }

    public static JwtUser create (UserEntity userEntity) {
        return new JwtUser(
                userEntity.getId(),
                userEntity.getUserName(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                mapToGrandAuthorities(new ArrayList<>(userEntity.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapToGrandAuthorities (List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())                )
                .collect(Collectors.toList());
    }
}
