package br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.entities.JpaBudgetEntity;

@Repository
public interface JpaBudgetRepository extends JpaRepository<JpaBudgetEntity, UUID> {
    @Query(
        value = """
                SELECT b.*
                FROM budget b
                WHERE 
                    b.user_id = :userId
                AND
                    :now BETWEEN b.start_date AND b.end_date
                """, 
        nativeQuery = true)
    Page<JpaBudgetEntity> findCurrent(
            @Param("userId") String userId,
            @Param("now") LocalDateTime now,
            Pageable pageable);
}
