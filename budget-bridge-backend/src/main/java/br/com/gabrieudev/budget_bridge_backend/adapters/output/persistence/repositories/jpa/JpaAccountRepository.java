package br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.entities.JpaAccountEntity;

@Repository
public interface JpaAccountRepository extends JpaRepository<JpaAccountEntity, UUID> {
    @Query(
        value = """
                SELECT a.*
                FROM account a
                WHERE 
                    a.user_id = :userId
                AND
                    (name IS NULL OR name LIKE CONCAT('%', :name, '%'))
                AND
                    (type IS NULL OR type = :type)
                AND
                    (currency IS NULL OR currency = :currency)
                """,
        nativeQuery = true
    )
    Page<JpaAccountEntity> findAllByCriteria(
            @Param("userId") String userId,
            @Param("name") String name,
            @Param("type") String type,
            @Param("currency") String currency,
            Pageable pageable);

    boolean existsByUserIdAndName(String userId, String name);
}
