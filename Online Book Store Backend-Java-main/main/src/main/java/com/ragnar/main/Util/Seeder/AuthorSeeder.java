package com.ragnar.main.Util.Seeder;

import com.ragnar.main.Application.IRepositories.IAuthorRepository;
import com.ragnar.main.Domain.Entities.Authors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorSeeder {
    private final IAuthorRepository authorRepository;

    public void SeedAuthors() {
        if (authorRepository.count() == 0) {
            authorRepository.saveAll(List.of(
                new Authors(null, "Tolkien", null),
                new Authors(null, "George R.R. Martin", null),
                new Authors(null, "J.K. Rowling", null)
            ));
            System.out.println("Default authors seeded.");
        }
    }
}
