package com.ragnar.main.Domain.Entities;

import com.ragnar.main.Domain.Enums.RoleType;
import com.ragnar.main.Domain.Shared.Audit;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Roles extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long roleId;

    @Enumerated(EnumType.STRING)
    public RoleType roleName;
}
