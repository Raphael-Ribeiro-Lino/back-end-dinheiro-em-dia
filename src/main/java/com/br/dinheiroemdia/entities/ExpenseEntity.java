package com.br.dinheiroemdia.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.br.dinheiroemdia.enums.ValueTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_expenses")
@Getter
@Setter
public class ExpenseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "date")
	private LocalDateTime date;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private ValueTypeEnum type;
	
	@ManyToOne
	@JoinColumn(name = "budget_id")
	private BudgetEntity budget;
}