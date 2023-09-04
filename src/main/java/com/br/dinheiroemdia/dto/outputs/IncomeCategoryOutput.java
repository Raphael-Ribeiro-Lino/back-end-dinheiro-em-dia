package com.br.dinheiroemdia.dto.outputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeCategoryOutput {

	private Long id;
	
	private String name;
	
	private IncomeOutput income;
}
