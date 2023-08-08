package com.br.dinheiroemdia.controllers.user;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

import com.br.dinheiroemdia.dto.inputs.UserInput;
import com.br.dinheiroemdia.utils.MyMvcMock;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class RegisterUserControllerTest {
	@Autowired
	private MyMvcMock mvc;

	private String uri;

	private UserInput userInput;

	@BeforeEach
	void setUp() throws Exception {

		this.uri = "/api-dinheiro-em-dia/user";

		this.userInput = new UserInput();
		this.userInput.setName("Alan Mathison Turing");
		this.userInput.setEmail("alan.exemplo@email.com");
		this.userInput.setPassword("@Mat1234");
	}

	@Test
	void quando_cadastrarUsuarioSemNome_Entao_RetornaErroNomeObrigatorio() throws Exception {
		this.userInput.setName("");
		ResultActions result = mvc.createdWithBadRequest(uri, userInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O nome é obrigatório')]").exists());
	}

	@Test
	void quando_cadastrarUsuarioNomeNulo_Entao_RetornaErroNomeObrigatorio() throws Exception {
		this.userInput.setName(null);
		ResultActions result = mvc.createdWithBadRequest(uri, userInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O nome é obrigatório')]").exists());
	}

	@Test
	void quando_cadastrarUsuarioNomeFormatoInvalido_Entao_RetornaErroNomeFormatoInvalido() throws Exception {
		this.userInput.setName("4lan");
		ResultActions result = mvc.createdWithBadRequest(uri, userInput);
		result.andExpect(jsonPath(
				"$.campos[?(@.message == 'Nome inválido. O nome deve atender aos seguintes critérios: Começar com uma ou mais letras maiúsculas ou minúsculas ou letras acentuadas comuns (à, é, í, etc.). Pode incluir um espaço ou hífen seguido por mais letras maiúsculas, minúsculas ou letras acentuadas comuns. O nome não pode conter números ou outros caracteres especiais.')]")
				.exists());
	}

	@Test
	void quando_cadastrarUsuarioNomeTamanhoInvalido_Entao_RetornaErroNomeTamanhoInvalido() throws Exception {
		this.userInput.setName(
				"Alanadsdhjsadhasjkdhkjdhsjkahddsjd Mathison Turingdaskldjulikrioeujrjlehfdjhfjdsklfdjsfsdkljfsdklfjkl");
		ResultActions result = mvc.createdWithBadRequest(uri, userInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O nome deve ter no máximo 100 caracteres')]").exists());
	}

	@Test
	void quando_cadastrarUsuarioSemEmail_Entao_RetornaErroEmailObrigatorio() throws Exception {
		this.userInput.setEmail("");
		ResultActions result = mvc.createdWithBadRequest(uri, userInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O e-mail é obrigatório')]").exists());
	}

	@Test
	void quando_cadastrarUsuarioEmailNulo_Entao_RetornaErroEmailObrigatorio() throws Exception {
		this.userInput.setEmail(null);
		ResultActions result = mvc.createdWithBadRequest(uri, userInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O e-mail é obrigatório')]").exists());
	}

	@Test
	void quando_cadastrarUsuarioEmailFormatoInvalido_Entao_RetornaErroEmailFormatoInvalido() throws Exception {
		this.userInput.setEmail("email");
		ResultActions result = mvc.createdWithBadRequest(uri, userInput);
		result.andExpect(jsonPath(
				"$.campos[?(@.message == 'Endereço de e-mail inválido. O endereço de e-mail deve atender aos seguintes critérios: Deve começar com um ou mais caracteres alfanuméricos, traços ou sublinhados. Deve conter o símbolo @. O domínio do e-mail deve começar com um caractere alfanumérico ou sublinhado e pode incluir pontos seguidos por mais caracteres alfanuméricos ou sublinhados. O domínio do e-mail deve ter pelo menos duas letras após o último ponto.')]")
				.exists());
	}

	@Test
	void quando_cadastrarUsuarioEmailTamanhoInvalido_Entao_RetornaErroEmailTamanhoInvalido() throws Exception {
		this.userInput.setEmail(
				"exemplo321abcdefghijklmnopqfddsfsdfsdfsdfsdfdsfsdkfjsdkfsdfjsdlfjsdfjskldjfsdkljfklsdjfklsdjfklsdjfklsdjfklsjfklsdjflksdjfklsdjfksdjfklsdjflksdjfklsdjflksdjfklsdjfklsdjflksdjfkldsjfskldjfklsjfkdjfksdjfkdjfkdjfkdjsfkdjsfkosdljfdklsjflsdkfjskldfjdsklfjsrstuvwxyz1234567890@example321abcdefghijklmnopqrstuvwxyz1234567890.com");
		ResultActions result = mvc.createdWithBadRequest(uri, userInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O e-mail deve ter no máximo 320 caracteres')]").exists());
	}

	@Test
	void quando_cadastrarUsuarioEmailJaCadastrado_Entao_RetornaErroEmailJaCadastrado() throws Exception {
		this.userInput.setEmail("raphar.lino@gmail.com");
		ResultActions result = mvc.createdWithBadRequest(uri, userInput);
		result.andExpect(jsonPath(
				"$.[?(@.message == 'O endereço de e-mail já está registrado. Por favor, escolha um endereço de e-mail diferente ou faça login na sua conta existente')]")
				.exists());
	}
	
	@Test
	void quando_cadastrarUsuarioSemSenha_Entao_RetornaErroSenhaObrigatorio() throws Exception {
		this.userInput.setPassword("");
		ResultActions result = mvc.createdWithBadRequest(uri, userInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'A senha é obrigatória')]").exists());
	}
	
	@Test
	void quando_cadastrarUsuarioSenhaNula_Entao_RetornaErroSenhaObrigatorio() throws Exception {
		this.userInput.setPassword(null);
		ResultActions result = mvc.createdWithBadRequest(uri, userInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'A senha é obrigatória')]").exists());
	}
	
	@Test
	void quando_cadastrarUsuarioSenhaTamanhoMaximoInvalido_Entao_RetornaErroSenhaTamanhoInvalido() throws Exception {
		this.userInput.setPassword("exemplo321abcdefghijklmnopqrstuvwxyz12345adsadasasdasdasdasdasdasdsadasdasm,jdkajsdlkajslkdlkassajdasjdlkajdklsjdklsjalkdjaskldjklasjdklasdjklasjdklasjdkasjdklasjdklasjdklasjdklasjdjdjdjjddjjdjdjdjsj67890@example321abcdefghijklmnopqrstuvwxyz1234567890.com.br");	
		ResultActions result = mvc.createdWithBadRequest(uri, userInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'A senha deve ter no mínimo 8 caracteres e no máximo 255 caracteres')]").exists());
	}
	
	@Test
	void quando_cadastrarUsuarioSenhaTamanhoMinimoInvalido_Entao_RetornaErroSenhaTamanhoInvalido() throws Exception {
		this.userInput.setPassword("@Rapha1");
		ResultActions result = mvc.createdWithBadRequest(uri, userInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'A senha deve ter no mínimo 8 caracteres e no máximo 255 caracteres')]").exists());
	}
	
	@Test
	void quando_cadastrarUsuario_Entao_RetornaSucesso() throws Exception{
		ResultActions result = mvc.create(uri, userInput);
		result.andExpect(jsonPath("id").exists());
		result.andExpect(jsonPath("name").value(userInput.getName()));
		result.andExpect(jsonPath("email").value(userInput.getEmail()));
	}
}