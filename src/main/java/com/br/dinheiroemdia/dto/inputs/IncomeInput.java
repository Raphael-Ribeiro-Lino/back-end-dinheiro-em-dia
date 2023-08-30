package com.br.dinheiroemdia.dto.inputs;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeInput {

	@NotBlank(message = "O nome é obrigatório")
	@Length(max = 255, message = "O nome deve ter no máximo 255 caracteres")
	private String name;

	@NotNull(message = "O valor é obrigatório")
	@Positive(message = "O valor deve ser positivo")
	private BigDecimal amount;

	@NotNull(message = "O orçamento é obrigatório")
	private Long budgetId;
}
