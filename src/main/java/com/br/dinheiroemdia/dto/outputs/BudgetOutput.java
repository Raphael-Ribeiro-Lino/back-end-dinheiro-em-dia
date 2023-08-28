package com.br.dinheiroemdia.dto.outputs;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import com.br.dinheiroemdia.entities.ExpenseEntity;
import com.br.dinheiroemdia.entities.IncomeEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetOutput {

	private Long id;
	
	private YearMonth yearMonth;
	
	private List<IncomeEntity> incomes;
	
	private BigDecimal totalIncome;
	
	private List<ExpenseEntity> expenses;
	
	private BigDecimal totalExpense;
	
	private BigDecimal netBalance;
	
	private BigDecimal plannedBudget;
	
	private BigDecimal actualBudget;
}
