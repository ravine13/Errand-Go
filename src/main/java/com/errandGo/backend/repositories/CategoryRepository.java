package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT r FROM Category r WHERE r.id = :id ")
    Optional<Category> findById(Long id);
}
