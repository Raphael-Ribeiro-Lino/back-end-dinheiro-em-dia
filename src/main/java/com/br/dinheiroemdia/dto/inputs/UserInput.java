package com.br.dinheiroemdia.dto.inputs;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput {

	@NotBlank(message = "O nome é obrigatório")
	@Length(max = 100, message = "O nome deve ter no máximo 100 caracteres")
	@Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:[ -][A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "Nome inválido. O nome deve atender aos seguintes critérios: Começar com uma ou mais letras maiúsculas ou minúsculas ou letras acentuadas comuns (à, é, í, etc.). Pode incluir um espaço ou hífen seguido por mais letras maiúsculas, minúsculas ou letras acentuadas comuns. O nome não pode conter números ou outros caracteres especiais.")
	private String name;

	@NotBlank(message = "O e-mail é obrigatório")
	@Length(max = 320, message = "O e-mail deve ter no máximo 320 caracteres")
	@Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Endereço de e-mail inválido. O endereço de e-mail deve atender aos seguintes critérios: Deve começar com um ou mais caracteres alfanuméricos, traços ou sublinhados. Deve conter o símbolo @. O domínio do e-mail deve começar com um caractere alfanumérico ou sublinhado e pode incluir pontos seguidos por mais caracteres alfanuméricos ou sublinhados. O domínio do e-mail deve ter pelo menos duas letras após o último ponto.")
	private String email;

	@NotBlank(message = "A senha é obrigatória")
	@Length(min = 8, max = 255, message = "A senha deve ter no mínimo 8 caracteres e no máximo 255 caracteres")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Senha inválida. A senha deve atender aos seguintes critérios: Ter pelo menos 8 caracteres, incluir pelo menos uma letra maiúscula ou minúscula, incluir pelo menos um número e incluir pelo menos um caractere especial entre @, $, !, %, *, ?, &.")
	private String password;

}
