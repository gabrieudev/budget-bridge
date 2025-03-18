package br.com.gabrieudev.budget_bridge_backend.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.gabrieudev.budget_bridge_backend.domain.enums.AccountCurrencyEnum;
import br.com.gabrieudev.budget_bridge_backend.domain.enums.AccountTypeEnum;

public class Account {
    private UUID id;
    private String userId;
    private String name;
    private AccountTypeEnum type;
    private AccountCurrencyEnum currency;
    private BigDecimal balance;
    private String color;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public AccountTypeEnum getType() {
        return type;
    }
    public void setType(AccountTypeEnum type) {
        this.type = type;
    }
    public AccountCurrencyEnum getCurrency() {
        return currency;
    }
    public void setCurrency(AccountCurrencyEnum currency) {
        this.currency = currency;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
	
    public Account(UUID id, String userId, String name, AccountTypeEnum type, AccountCurrencyEnum currency,
			BigDecimal balance, String color, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.type = type;
		this.currency = currency;
		this.balance = balance;
		this.color = color;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
    public Account() {
	}
}
