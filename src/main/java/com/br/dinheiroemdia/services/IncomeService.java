package com.br.dinheiroemdia.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.dinheiroemdia.entities.BudgetEntity;
import com.br.dinheiroemdia.entities.IncomeEntity;
import com.br.dinheiroemdia.exceptions.NotFoundBussinessException;
import com.br.dinheiroemdia.repositories.IncomeRepository;

import jakarta.transaction.Transactional;

@Service
public class IncomeService {

	@Autowired
	private IncomeRepository incomeRepository;

	@Autowired
	private BudgetService budgetService;

	@Autowired
	private TokenService tokenService;

	@Transactional
	public IncomeEntity register(IncomeEntity incomeEntity, Long budgetId) {
		incomeEntity.setBudget(budgetService.findById(budgetId));
		return incomeRepository.save(incomeEntity);
	}

	public void updateBudget(IncomeEntity income) {
		BudgetEntity budgetEntity = budgetService.findById(income.getBudget().getId());
		budgetEntity.setTotalIncome(defineTotalIncome());
		budgetService.update(budgetEntity);
	}

	private BigDecimal defineTotalIncome() {
		List<IncomeEntity> incomes = incomeRepository.findAll();
		BigDecimal totalIncome = BigDecimal.ZERO;
		for (IncomeEntity incomeEntity : incomes) {
			totalIncome = totalIncome.add(incomeEntity.getAmount());
		}
		return totalIncome;
	}

	public IncomeEntity findById(Long id) {
		IncomeEntity incomeEntity = incomeRepository.findById(id).orElseThrow(() -> new NotFoundBussinessException("Receita " + id + " não encontrada"));
		if(incomeEntity.getBudget().getUser() == tokenService.getUserByToken()) {
			return incomeEntity;
		}else {
			throw new NotFoundBussinessException("Receita " + id + " não encontrada");
		}
	}

	@Transactional
	public void delete(IncomeEntity incomeEntity) {
		incomeRepository.delete(incomeEntity);
	}
}
