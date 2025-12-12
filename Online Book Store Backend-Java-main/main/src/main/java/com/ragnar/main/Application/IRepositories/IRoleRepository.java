package com.ragnar.main.Application.IRepositories;

import com.ragnar.main.Domain.Entities.Roles;
import com.ragnar.main.Domain.Enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRoleName(RoleType roleName);
}
