package ru.otus.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    List<Compilation> findByPinnedOrderById(Boolean pinned, Pageable pageable);
}
