package com.br.dinheiroemdia.services;

import java.time.YearMonth;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.dinheiroemdia.entities.BudgetEntity;
import com.br.dinheiroemdia.exceptions.BadRequestBussinessException;
import com.br.dinheiroemdia.exceptions.NotFoundBussinessException;
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

	public BudgetEntity findById(Long id) {
		return budgetRepository.findByIdAndUser(id, tokenService.getUserByToken())
				.orElseThrow(() -> new NotFoundBussinessException("Orçamento " + id + " não encontrado"));
	}

	public void verifyYearMonth(YearMonth yearMonth) {
		Optional<BudgetEntity> budgetOptional = budgetRepository.findByYearMonthAndUser(yearMonth,
				tokenService.getUserByToken());
		if(budgetOptional.isPresent()) {
			throw new BadRequestBussinessException("Orçamento já cadastrado");
		}
	}

	@Transactional
	public void updateTotalIncome(BudgetEntity budgetEntity) {
		budgetRepository.save(budgetEntity);
	}

	@Transactional
	public void updateTotalExpense(BudgetEntity budgetEntity) {
		budgetRepository.save(budgetEntity);
	}
}
