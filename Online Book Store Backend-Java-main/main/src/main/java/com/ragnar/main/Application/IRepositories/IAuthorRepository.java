package com.ragnar.main.Application.IRepositories;

import com.ragnar.main.Domain.Entities.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorRepository extends JpaRepository<Authors, Long> {
}
