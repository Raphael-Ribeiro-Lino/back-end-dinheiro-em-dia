package com.br.dinheiroemdia.dto.inputs;

import java.time.YearMonth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetInput {

	@NotNull(message = "O mês e ano é obrigatório")
	private YearMonth yearMonth;
}
