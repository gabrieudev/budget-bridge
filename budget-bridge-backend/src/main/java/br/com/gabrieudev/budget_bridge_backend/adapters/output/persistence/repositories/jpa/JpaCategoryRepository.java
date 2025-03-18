package br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.entities.JpaCategoryEntity;

@Repository
public interface JpaCategoryRepository extends JpaRepository<JpaCategoryEntity, UUID> {
    @Query(
        value = """
                SELECT c.*
                FROM category c
                WHERE 
                    c.user_id = :userId
                """,
        nativeQuery = true
    )
    Page<JpaCategoryEntity> findAllByCriteria(
            @Param("userId") String userId,
            Pageable pageable);

    boolean existsByUserIdAndName(String userId, String name);
}
