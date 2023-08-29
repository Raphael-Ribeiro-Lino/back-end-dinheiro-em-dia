package com.br.dinheiroemdia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.dinheiroemdia.entities.IncomeEntity;
import com.br.dinheiroemdia.repositories.IncomeRepository;

import jakarta.transaction.Transactional;

@Service
public class IncomeService {

	@Autowired
	private IncomeRepository incomeRepository;

	@Autowired
	private BudgetService budgetService;
	
	@Transactional
	public IncomeEntity register(IncomeEntity incomeEntity, Long budgetId) {
		incomeEntity.setBudget(budgetService.findById(budgetId));
		return incomeRepository.save(incomeEntity);
	}
}
