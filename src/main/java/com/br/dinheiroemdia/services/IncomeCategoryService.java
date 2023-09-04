package com.br.dinheiroemdia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.br.dinheiroemdia.entities.IncomeCategoryEntity;
import com.br.dinheiroemdia.entities.IncomeEntity;
import com.br.dinheiroemdia.exceptions.BadRequestBussinessException;
import com.br.dinheiroemdia.exceptions.NotFoundBussinessException;
import com.br.dinheiroemdia.repositories.IncomeCategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class IncomeCategoryService {

	@Autowired
	private IncomeCategoryRepository incomeCategoryRepository;

	@Autowired
	private IncomeService incomeService;

	@Autowired
	private TokenService tokenService;

	@Transactional
	public IncomeCategoryEntity register(IncomeCategoryEntity incomeCategoryEntity, Long incomeId) {
		incomeCategoryEntity.setIncome(incomeService.findById(incomeId));
		return incomeCategoryRepository.save(incomeCategoryEntity);
	}

	public void verifyCategory(String name, Long incomeId) {
		List<IncomeCategoryEntity> incomeCategories = incomeCategoryRepository.findAll();
		for (IncomeCategoryEntity incomeCategoryEntity : incomeCategories) {
			if (name.equals(incomeCategoryEntity.getName()) && incomeId.equals(incomeCategoryEntity.getIncome().getId())
					&& incomeCategoryEntity.getIncome().getBudget().getUser() == tokenService.getUserByToken()) {
				throw new BadRequestBussinessException("Categoria " + name + " já cadastrada");
			}
		}
	}

	public Page<IncomeCategoryEntity> list(Pageable pagination) {
		return incomeCategoryRepository.findAllByUser(pagination, tokenService.getUserByToken());
	}

	public IncomeCategoryEntity findById(Long id) {
		return incomeCategoryRepository.findByIdAndUser(id, tokenService.getUserByToken())
				.orElseThrow(() -> new NotFoundBussinessException("Categoria " + id + " não encontrada"));
	}

	@Transactional
	public void delete(IncomeCategoryEntity incomeCategoryEntity) {
		incomeCategoryRepository.delete(incomeCategoryEntity);
	}

	@Transactional
	public void deleteCategory(IncomeEntity incomeEntity) {
		List<IncomeCategoryEntity> finByIncomeAndUser = incomeCategoryRepository.finByIncomeAndUser(incomeEntity,
				tokenService.getUserByToken());
		for (IncomeCategoryEntity incomeCategoryEntity : finByIncomeAndUser) {
			delete(incomeCategoryEntity);
		}
	}
}
