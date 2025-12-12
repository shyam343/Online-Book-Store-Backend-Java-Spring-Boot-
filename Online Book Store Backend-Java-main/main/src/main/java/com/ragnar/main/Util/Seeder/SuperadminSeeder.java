package com.ragnar.main.Util.Seeder;

import com.ragnar.main.API.DTOs.Auth.SignupDTO;
import com.ragnar.main.Application.IRepositories.IRoleRepository;
import com.ragnar.main.Application.IRepositories.IUserRepository;
import com.ragnar.main.Domain.Entities.Roles;
import com.ragnar.main.Domain.Entities.Users;
import com.ragnar.main.Domain.Enums.RoleType;
import com.ragnar.main.Util.Mappers.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SuperadminSeeder {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    @Autowired
    private AuthMapper mapper;

    public void SeedSuperAdmin() {
        var existingAdmin = userRepository.findByUsername("superadmin");
        if (existingAdmin == null) {
            SignupDTO adminUser = SignupDTO.builder()
                    .username("superadmin")
                    .firstName("default")
                    .lastName("admin")
                    .address("home")
                    .email("admin@admin.com")
                    .password("superadmin@123")
                    .phone("0123454321")
                    .build();

            Users userModel = mapper.mapSignupToUsers(adminUser);

            Roles superADminRole = roleRepository.findByRoleName(RoleType.ROLE_SUPERADMIN).orElseThrow();
            userModel.setRoles(List.of(superADminRole));
            userRepository.save(userModel);
            System.out.println("Default admin seeded.");
        }

        var existingUser = userRepository.findByUsername("actualtestuser");
        if (existingUser == null) {
            SignupDTO testUser = SignupDTO.builder()
                    .username("actualtestuser")
                    .firstName("actualtest")
                    .lastName("user")
                    .address("testhome")
                    .email("testuser@testuser.com")
                    .password("user@123")
                    .phone("0123454321")
                    .build();

            Users userModel = mapper.mapSignupToUsers(testUser);

            Roles userRole = roleRepository.findByRoleName(RoleType.ROLE_USER).orElseThrow();
            userModel.setRoles(List.of(userRole));
            userRepository.save(userModel);
            System.out.println("Default test user seeded.");
        }
    }
}
