package com.br.dinheiroemdia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.dinheiroemdia.entities.BudgetEntity;
import com.br.dinheiroemdia.repositories.BudgetRepository;

import jakarta.transaction.Transactional;

@Service
public class BudgetService {

	@Autowired
	private BudgetRepository budgetRepository;
	
	@Autowired
	private TokenService tokenService;

	@Transactional
	public BudgetEntity register(BudgetEntity budgetEntity) {
		budgetEntity.setUser(tokenService.getUserByToken());
		return budgetRepository.save(budgetEntity);
	}
}