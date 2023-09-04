package com.br.dinheiroemdia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.dinheiroemdia.entities.IncomeCategoryEntity;
import com.br.dinheiroemdia.repositories.IncomeCategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class IncomeCategoryService {

	@Autowired
	private IncomeCategoryRepository incomeCategoryRepository;
	
	@Autowired
	private IncomeService incomeService;

	@Transactional
	public IncomeCategoryEntity register(IncomeCategoryEntity incomeCategoryEntity, Long incomeId) {
		incomeCategoryEntity.setIncome(incomeService.findById(incomeId));
		return incomeCategoryRepository.save(incomeCategoryEntity);
	}
}
