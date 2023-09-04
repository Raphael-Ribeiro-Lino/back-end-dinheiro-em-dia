package com.br.dinheiroemdia.dto.outputs;

import java.math.BigDecimal;
import java.time.YearMonth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListBudgetOutput {

	private Long id;
	
	private YearMonth yearMonth;
	
	private BigDecimal totalIncome;
	
	private BigDecimal totalExpense;
	
	private BigDecimal plannedBudget;
	
	private BigDecimal actualBudget;
}
