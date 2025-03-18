package br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.entities.JpaGoalEntity;

@Repository
public interface JpaGoalRepository extends JpaRepository<JpaGoalEntity, UUID> {
    @Query(
        value = """
                SELECT g.*
                FROM goal g
                WHERE 
                    g.user_id = :userId
                AND
                    (:accountId IS NULL OR g.account_id = :accountId)
                AND
                    (:type IS NULL OR g.type = :type)
                AND
                    (:status IS NULL OR g.status = :status)
                """,
        nativeQuery = true
    )
    Page<JpaGoalEntity> findAllByCriteria(
            @Param("userId") String userId,
            @Param("accountId") UUID accountId,
            @Param("type") String type,
            @Param("status") String status,
            Pageable pageable);
}
