package com.br.dinheiroemdia.converts;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.dinheiroemdia.dto.inputs.IncomeInput;
import com.br.dinheiroemdia.dto.outputs.IncomeOutput;
import com.br.dinheiroemdia.entities.IncomeEntity;

import jakarta.validation.Valid;

@Component
public class IncomeConvert {

	@Autowired
	private ModelMapper modelMapper;

	public IncomeEntity inputToEntity(@Valid IncomeInput incomeInput) {
		return modelMapper.map(incomeInput, IncomeEntity.class);
	}

	public IncomeOutput entityToOutput(IncomeEntity income) {
		return modelMapper.map(income, IncomeOutput.class);
	}
}
