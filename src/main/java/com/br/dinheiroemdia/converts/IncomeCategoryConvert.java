package com.br.dinheiroemdia.converts;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.br.dinheiroemdia.dto.inputs.IncomeCategoryInput;
import com.br.dinheiroemdia.dto.outputs.IncomeCategoryOutput;
import com.br.dinheiroemdia.entities.IncomeCategoryEntity;

import jakarta.validation.Valid;

@Component
public class IncomeCategoryConvert {

	@Autowired
	private ModelMapper modelMapper;

	public IncomeCategoryEntity inputToEntity(@Valid IncomeCategoryInput incomeCategoryInput) {
		return modelMapper.map(incomeCategoryInput, IncomeCategoryEntity.class);
	}

	public IncomeCategoryOutput entityToOutput(IncomeCategoryEntity incomeCategory) {
		return modelMapper.map(incomeCategory, IncomeCategoryOutput.class);
	}

	public Page<IncomeCategoryOutput> pageEntityToOutput(Page<IncomeCategoryEntity> incomeCategories) {
		return incomeCategories.map(this::entityToOutput);
	}
}
