package com.br.dinheiroemdia.entities;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_budgets")
@Getter
@Setter
public class BudgetEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "date_year_month")
	private YearMonth yearMonth;
	
	@JoinColumn(name = "user_id")
	@ManyToOne
	private UserEntity user;
	
	@OneToMany(mappedBy = "budget")
	private List<IncomeEntity> incomes = new ArrayList<>();
	
	@Column(name = "total_income")
	private BigDecimal totalIncome = BigDecimal.ZERO;
	
	@OneToMany(mappedBy = "budget")
	private List<ExpenseEntity> expenses = new ArrayList<>();
	
	@Column(name = "total_expense")
	private BigDecimal totalExpense = BigDecimal.ZERO;
	
	@Column(name = "planned_budget")
	private BigDecimal plannedBudget;
	
	@Column(name = "actual_budget")
	private BigDecimal actualBudget = BigDecimal.ZERO;
}
