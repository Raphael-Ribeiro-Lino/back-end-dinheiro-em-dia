package com.br.dinheiroemdia.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.dinheiroemdia.entities.BudgetEntity;
import com.br.dinheiroemdia.entities.ExpenseEntity;
import com.br.dinheiroemdia.repositories.ExpenseRepository;

import jakarta.transaction.Transactional;

@Service
public class ExpenseService {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private BudgetService budgetService;

	@Transactional
	public ExpenseEntity register(ExpenseEntity expenseEntity, Long budgetId) {
		expenseEntity.setBudget(budgetService.findById(budgetId));
		return expenseRepository.save(expenseEntity);
	}

	public void updateBudget(ExpenseEntity expense) {
		BudgetEntity budgetEntity = budgetService.findById(expense.getBudget().getId());
		budgetEntity.setTotalExpense(defineTotalExpense());
		budgetService.update(budgetEntity);
	}
	
	private BigDecimal defineTotalExpense() {
		List<ExpenseEntity> expenses = expenseRepository.findAll();
		BigDecimal totalExpense = BigDecimal.ZERO;
		for (ExpenseEntity expenseEntity : expenses) {
			totalExpense = totalExpense.add(expenseEntity.getAmount());
		}
		return totalExpense;
	}

}
