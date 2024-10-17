package com.api.budget_bridge.repository;

import com.api.budget_bridge.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositoy extends JpaRepository<Category, Long> {
}
