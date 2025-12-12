package com.ragnar.main.Util.Seeder;

import com.ragnar.main.Application.IRepositories.IRoleRepository;
import com.ragnar.main.Domain.Entities.Roles;
import com.ragnar.main.Domain.Enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleSeeder {
    private final IRoleRepository roleRepository;

    public void SeedAllRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Roles(null, RoleType.ROLE_USER));
            roleRepository.save(new Roles(null, RoleType.ROLE_ADMIN));
            roleRepository.save(new Roles(null, RoleType.ROLE_SUPERADMIN));
            System.out.println("Default roles seeded.");
        }
    }
}
