package com.br.dinheiroemdia.dto.outputs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.br.dinheiroemdia.enums.ValueTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseOutput {

	private Long id;
	
	private String name;
	
	private BigDecimal amount;
	
	private LocalDateTime date;
	
	private ValueTypeEnum type;
}
