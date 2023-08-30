package com.br.dinheiroemdia.dto.outputs;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetOutput {

	private Long id;
	
	private YearMonth yearMonth;
	
	private List<IncomeOutput> incomes;
	
	private BigDecimal totalIncome;
	
	private List<ExpenseOutput> expenses;
	
	private BigDecimal totalExpense;
	
	private BigDecimal plannedBudget;
	
	private BigDecimal actualBudget;
}
