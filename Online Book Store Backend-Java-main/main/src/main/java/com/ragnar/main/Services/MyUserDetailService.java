package com.ragnar.main.Services;

import com.ragnar.main.Application.IRepositories.IUserRepository;
import com.ragnar.main.Domain.Entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {
    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        // TODO: need to check for null or deleted users and throw exceptions + logs.

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());

        // RETURN userdetails.User of spring security
        return new User(
            user.getUsername(),
            user.getPasswordHash(),
            authorities
        );
    }
}
