package com.ragnar.main.Application.IRepositories;

import com.ragnar.main.Domain.Entities.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILogRepository extends JpaRepository<Logs, Long> {
}
