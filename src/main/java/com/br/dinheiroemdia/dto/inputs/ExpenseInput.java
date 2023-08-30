package com.br.dinheiroemdia.dto.inputs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import com.br.dinheiroemdia.enums.ValueTypeEnum;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseInput {

	@NotNull(message = "O nome é obrigatório")
	@Length(max = 255, message = "O nome deve ter no máximo 255 caracteres")
	private String name;
	
	@NotNull(message = "O valor é obrigatório")
	@Positive(message = "O valor deve ser positivo")
	private BigDecimal amount;
	
	@NotNull(message = "A data é obrigatória")
	private LocalDateTime date;
	
	@NotNull(message = "O tipo é obrigatório")
	private ValueTypeEnum type;
	
	@NotNull(message = "O orçamento é obrigatório")
	private Long budgetId;
}
