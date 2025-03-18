package br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.budget_bridge_backend.domain.entities.Goal;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.GoalStatusEnum;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.GoalTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "goal")
public class JpaGoalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private JpaAccountEntity account;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "target_amount", nullable = false)
    private BigDecimal targetAmount;
    
    @Column(name = "current_amount", nullable = false)
    private BigDecimal currentAmount;
    
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private GoalTypeEnum type;
    
    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private GoalStatusEnum status;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static JpaGoalEntity fromDomain(Goal goal) {
        return new ModelMapper().map(goal, JpaGoalEntity.class);
    }

    public void update(Goal goal) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(goal, this);
    }

    public Goal toDomain() {
        return new ModelMapper().map(this, Goal.class);
    }
}
