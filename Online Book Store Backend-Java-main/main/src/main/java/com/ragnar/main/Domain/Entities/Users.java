package com.ragnar.main.Domain.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ragnar.main.Domain.Shared.Audit;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Users extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String passwordHash;

    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;

    private String address;
    private boolean isDeleted = false; // default is false so

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Roles> roles;
}
