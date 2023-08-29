package com.br.dinheiroemdia.dto.outputs;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeOutput {

	private Long id;
	
	private String name;
	
	private BigDecimal amount;
	
	private IncomeBudgetOutput budget;
}
