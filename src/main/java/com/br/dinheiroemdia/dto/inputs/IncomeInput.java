package com.br.dinheiroemdia.dto.inputs;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeInput {

	private String name;

	private BigDecimal amount;

	private Long budgetId;
}
