package com.br.dinheiroemdia.converts;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.br.dinheiroemdia.dto.inputs.BudgetInput;
import com.br.dinheiroemdia.dto.outputs.BudgetOutput;
import com.br.dinheiroemdia.dto.outputs.ListBudgetOutput;
import com.br.dinheiroemdia.entities.BudgetEntity;

import jakarta.validation.Valid;

@Component
public class BudgetConvert {

	@Autowired
	private ModelMapper modelMapper;

	public BudgetEntity inputToEntity(@Valid BudgetInput budgetInput) {
		return modelMapper.map(budgetInput, BudgetEntity.class);
	}

	public BudgetOutput entityToOutput(BudgetEntity budget) {
		return modelMapper.map(budget, BudgetOutput.class);
	}

	public ListBudgetOutput listEntityToOutput(BudgetEntity budget) {
		return modelMapper.map(budget, ListBudgetOutput.class);
	}
	
	public Page<ListBudgetOutput> pageEntityToOutput(Page<BudgetEntity> budgets) {
		return budgets.map(this::listEntityToOutput);
	}
}
