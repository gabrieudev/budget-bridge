package br.com.gabrieudev.budget_bridge_backend.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.domain.enums.TransactionTypeEnum;

public class Transaction {
    private UUID id;
    private String userId;
    private Account account;
    private Goal goal;
    private TransactionTypeEnum type;
    private Category category;
    private BigDecimal amount;
    private String description;
    private LocalDateTime transactionDate;
    private LocalDateTime createdAt;
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    public Goal getGoal() {
        return goal;
    }
    public void setGoal(Goal goal) {
        this.goal = goal;
    }
    public TransactionTypeEnum getType() {
        return type;
    }
    public void setType(TransactionTypeEnum type) {
        this.type = type;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Transaction(UUID id, String userId, Account account, Goal goal, TransactionTypeEnum type, Category category,
            BigDecimal amount, String description, LocalDateTime transactionDate, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.account = account;
        this.goal = goal;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.createdAt = createdAt;
    }
    
    public Transaction() {
    }
}
