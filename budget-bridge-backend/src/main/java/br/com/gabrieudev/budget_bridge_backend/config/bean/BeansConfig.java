package br.com.gabrieudev.budget_bridge_backend.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.AccountRepositoryAdapter;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.BudgetRepositoryAdapter;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.CategoryRepositoryAdapter;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.GoalRepositoryAdapter;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.TransactionRepositoryAdapter;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa.JpaAccountRepository;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa.JpaBudgetRepository;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa.JpaCategoryRepository;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa.JpaGoalRepository;
import br.com.gabrieudev.budget_bridge_backend.adapters.output.persistence.repositories.jpa.JpaTransactionRepository;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.AccountOutputPort;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.BudgetOutputPort;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.CategoryOutputPort;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.GoalOutputPort;
import br.com.gabrieudev.budget_bridge_backend.application.ports.output.TransactionOutputPort;
import br.com.gabrieudev.budget_bridge_backend.application.services.AccountService;
import br.com.gabrieudev.budget_bridge_backend.application.services.BudgetService;
import br.com.gabrieudev.budget_bridge_backend.application.services.CategoryService;
import br.com.gabrieudev.budget_bridge_backend.application.services.GoalService;
import br.com.gabrieudev.budget_bridge_backend.application.services.TransactionService;

@Configuration
public class BeansConfig {
    @Bean
    AccountService accountService(AccountOutputPort accountOutputPort) {
        return new AccountService(accountOutputPort);
    }

    @Bean
    AccountOutputPort accountOutputPort(JpaAccountRepository jpaAccountRepository) {
        return new AccountRepositoryAdapter(jpaAccountRepository);
    }

    @Bean
    BudgetService budgetService(BudgetOutputPort budgetOutputPort) {
        return new BudgetService(budgetOutputPort);
    }

    @Bean
    BudgetOutputPort budgetOutputPort(JpaBudgetRepository jpaBudgetRepository) {
        return new BudgetRepositoryAdapter(jpaBudgetRepository);
    }

    @Bean
    CategoryService categoryService(CategoryOutputPort categoryOutputPort, TransactionOutputPort transactionOutputPort) {
        return new CategoryService(categoryOutputPort, transactionOutputPort);
    }

    @Bean
    CategoryOutputPort categoryOutputPort(JpaCategoryRepository jpaCategoryRepository) {
        return new CategoryRepositoryAdapter(jpaCategoryRepository);
    }

    @Bean
    GoalService goalService(GoalOutputPort goalOutputPort, AccountOutputPort accountOutputPort) {
        return new GoalService(goalOutputPort, accountOutputPort);
    }

    @Bean
    GoalOutputPort goalOutputPort(JpaGoalRepository jpaGoalRepository) {
        return new GoalRepositoryAdapter(jpaGoalRepository);
    }

    @Bean
    TransactionService transactionService(TransactionOutputPort transactionOutputPort, AccountOutputPort accountOutputPort) {
        return new TransactionService(transactionOutputPort, accountOutputPort);
    }

    @Bean
    TransactionOutputPort transactionOutputPort(JpaTransactionRepository jpaTransactionRepository, JpaCategoryRepository jpaCategoryRepository) {
        return new TransactionRepositoryAdapter(jpaTransactionRepository, jpaCategoryRepository);
    }
}
