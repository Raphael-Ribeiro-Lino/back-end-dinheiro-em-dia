package com.br.dinheiroemdia.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.dinheiroemdia.entities.BudgetEntity;
import com.br.dinheiroemdia.entities.ExpenseEntity;
import com.br.dinheiroemdia.exceptions.NotFoundBussinessException;
import com.br.dinheiroemdia.repositories.ExpenseRepository;

import jakarta.transaction.Transactional;

@Service
public class ExpenseService {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private BudgetService budgetService;
	
	@Autowired
	private TokenService tokenService;

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

	public ExpenseEntity findById(Long id) {
		ExpenseEntity expenseEntity = expenseRepository.findById(id).orElseThrow(() -> new NotFoundBussinessException("Despesa " + id + " não encontrada"));
		if(expenseEntity.getBudget().getUser() == tokenService.getUserByToken()) {
			return expenseEntity;
		}else {
			throw new NotFoundBussinessException("Despesa " + id + " não encontrada");
		}
	}

	@Transactional
	public void delete(ExpenseEntity expenseEntity) {
		expenseRepository.delete(expenseEntity);
	}

}
