package com.ragnar.main.Domain.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ragnar.main.Domain.Enums.GenreType;
import com.ragnar.main.Domain.Shared.Audit;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Books extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String bookName;
    private Date publicationYear;

    @Enumerated(value = EnumType.STRING)
    private GenreType Genre;

    private double Price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId", nullable = false)
    private Authors author;

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookings> bookings;
}
