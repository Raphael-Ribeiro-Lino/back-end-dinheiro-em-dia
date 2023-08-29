package com.br.dinheiroemdia.dto.outputs;

import java.time.YearMonth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeBudgetOutput {

	private Long id;
	
	private YearMonth yearMonth;
}
