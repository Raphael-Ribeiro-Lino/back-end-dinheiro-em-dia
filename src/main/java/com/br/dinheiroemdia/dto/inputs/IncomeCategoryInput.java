package com.br.dinheiroemdia.dto.inputs;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeCategoryInput {

	@NotBlank(message = "O nome é obrigatório")
	@Length(max = 255, message = "O nome deve ter no máximo 255 caracteres")
	private String name;
	
	@NotNull(message = "A receita é obrigatória")
	private Long incomeId;
}
