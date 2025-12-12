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
public class Authors extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    private String authorName;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Books> books;
}
