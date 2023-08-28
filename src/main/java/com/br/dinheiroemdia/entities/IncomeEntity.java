package com.br.dinheiroemdia.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_incomes")
@Getter
@Setter
public class IncomeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;	

	@Column(name = "name")
	private String name;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
	@ManyToOne
    @JoinColumn(name = "budget_id")
    private BudgetEntity budget;
}
