package br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.entities.JpaCategoryEntity;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.entities.JpaTransactionEntity;

@Repository
public interface JpaTransactionRepository extends JpaRepository<JpaTransactionEntity, UUID> {
    @Query(
        value = """
                SELECT t.*
                FROM transaction t
                WHERE 
                    t.user_id = :userId
                AND
                    (:accountId IS NULL OR t.account_id = :accountId)
                AND
                    (:type IS NULL OR t.type = :type)
                AND
                    (:categoryId IS NULL OR t.category_id = :categoryId)
                AND
                    (
                        (:startDate IS NULL OR :endDate IS NULL)
                    OR
                        (t.date BETWEEN :startDate AND :endDate)
                    )
                """,
        nativeQuery = true
    )
    Page<JpaTransactionEntity> findAllByCriteria(
            @Param("userId") String userId,
            @Param("accountId") UUID accountId,
            @Param("type") String type,
            @Param("categoryId") UUID categoryId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    boolean existsByCategory(JpaCategoryEntity category);
}
