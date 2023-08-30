package com.br.dinheiroemdia.converts;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.dinheiroemdia.dto.inputs.ExpenseInput;
import com.br.dinheiroemdia.dto.outputs.ExpenseOutput;
import com.br.dinheiroemdia.entities.ExpenseEntity;

import jakarta.validation.Valid;

@Component
public class ExpenseConvert {

	@Autowired
	private ModelMapper modelMapper;

	public ExpenseEntity inputToEntity(@Valid ExpenseInput expenseInput) {
		return modelMapper.map(expenseInput, ExpenseEntity.class);
	}

	public ExpenseOutput entityToOutput(ExpenseEntity expense) {
		return modelMapper.map(expense, ExpenseOutput.class);
	}
}
