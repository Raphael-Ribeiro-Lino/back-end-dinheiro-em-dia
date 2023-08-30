package com.br.dinheiroemdia.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

import com.br.dinheiroemdia.configs.ControllerConfig;
import com.br.dinheiroemdia.dto.inputs.LoginInput;
import com.br.dinheiroemdia.utils.MyMvcMock;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class LoginControllerTest {

	@Autowired
	private MyMvcMock mvc;

	private String uri;
	private LoginInput loginInput;

	@BeforeEach
	void setUp() throws Exception {

		this.uri = ControllerConfig.PRE_URL + "/auth";

		loginInput = new LoginInput();
		loginInput.setEmail("raphar.lino@gmail.com");
		loginInput.setPassword("@Rapha12345678");
	}

	@Test
	void quando_realizarLoginSemEmail_Entao_RetornaErroEmailObrigatorio() throws Exception {
		this.loginInput.setEmail("");
		ResultActions result = mvc.createdWithBadRequest(uri, loginInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O e-mail é obrigatório')]").exists());
	}

	@Test
	void quando_realizarLoginEmailNulo_Entao_RetornaErroEmailObrigatorio() throws Exception {
		this.loginInput.setEmail(null);
		ResultActions result = mvc.createdWithBadRequest(uri, loginInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O e-mail é obrigatório')]").exists());
	}

	@Test
	void quando_realizarLoginEmailFormatoInvalido_Entao_RetornaErroEmailFormatoInvalido() throws Exception {
		this.loginInput.setEmail("emailInvalido.com");
		ResultActions result = mvc.createdWithBadRequest(uri, loginInput);
		result.andExpect(jsonPath(
				"$.campos[?(@.message == 'Endereço de e-mail inválido. O endereço de e-mail deve atender aos seguintes critérios: Deve começar com um ou mais caracteres alfanuméricos, traços ou sublinhados. Deve conter o símbolo @. O domínio do e-mail deve começar com um caractere alfanumérico ou sublinhado e pode incluir pontos seguidos por mais caracteres alfanuméricos ou sublinhados. O domínio do e-mail deve ter pelo menos duas letras após o último ponto.')]")
				.exists());
	}

	@Test
	void quando_realizarLoginEmailTamanhoInvalido_Entao_RetornaErroEmailTamanhoInvalido() throws Exception {
		this.loginInput.setEmail(
				"exemplo321abcdefghijklmnopqfddsfsdfsdfsdfsdfdsfsdkfjsdkfsdfjsdlfjsdfjskldjfsdkljfklsdjfklsdjfklsdjfklsdjfklsjfklsdjflksdjfklsdjfksdjfklsdjflksdjfklsdjflksdjfklsdjfklsdjflksdjfkldsjfskldjfklsjfkdjfksdjfkdjfkdjfkdjsfkdjsfkosdljfdklsjflsdkfjskldfjdsklfjsrstuvwxyz1234567890@example321abcdefghijklmnopqrstuvwxyz1234567890.com");
		ResultActions result = mvc.createdWithBadRequest(uri, loginInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O e-mail deve ter no máximo 320 caracteres')]").exists());
	}

	@Test
	void quando_realizarLoginSemSenha_Entao_RetornaErroSenhaObrigatorio() throws Exception {
		this.loginInput.setPassword("");
		ResultActions result = mvc.createdWithBadRequest(uri, loginInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'A senha é obrigatória')]").exists());
	}

	@Test
	void quando_realizarLoginSenhaNulo_Entao_RetornaErroSenhaObrigatorio() throws Exception {
		this.loginInput.setPassword(null);
		ResultActions result = mvc.createdWithBadRequest(uri, loginInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'A senha é obrigatória')]").exists());
	}

	@Test
	void quando_realizarLoginSenhaTamanhoMaximoInvalido_Entao_RetornaErroSenhaTamanhoInvalido() throws Exception {
		this.loginInput.setPassword("exemplo321abcdefghijklmnopqrstuvwxyz12345adsadasasdasdasdasdasdasdsadasdasm,jdkajsdlkajslkdlkassajdasjdlkajdklsjdklsjalkdjaskldjklasjdklasdjklasjdklasjdkasjdklasjdklasjdklasjdklasjdjdjdjjddjjdjdjdjsj67890@example321abcdefghijklmnopqrstuvwxyz1234567890.com.br");	
		ResultActions result = mvc.createdWithBadRequest(uri, loginInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'A senha deve ter no mínimo 8 caracteres e no máximo 255 caracteres')]").exists());
	}
	
	@Test
	void quando_realizarLoginSenhaTamanhoMinimoInvalido_Entao_RetornaErroSenhaTamanhoInvalido() throws Exception {
		this.loginInput.setPassword("@Rapha1");
		ResultActions result = mvc.createdWithBadRequest(uri, loginInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'A senha deve ter no mínimo 8 caracteres e no máximo 255 caracteres')]").exists());
	}
	
	@Test
	void quando_realizarLoginEmailInvalido_Entao_RetornaErroEmailOuSenhaInvalida() throws Exception {
		this.loginInput.setEmail("teste@gmail.com.br");
		ResultActions result = mvc.createdWithBadRequest(uri, loginInput);
		result.andExpect(jsonPath("$.[?(@.message == 'E-mail ou Senha Inválida')]").exists());
	}

	@Test
	void quando_realizarLoginSenhaInvalida_Entao_RetornaErroEmailOuSenhaInvalida() throws Exception {
		this.loginInput.setPassword("@Rapha1234567");
		ResultActions result = mvc.createdWithBadRequest(uri, loginInput);
		result.andExpect(jsonPath("$.[?(@.message == 'E-mail ou Senha Inválida')]").exists());
	}

	@Test
	void quando_realizarLogin_Entao_RetornaSucesso() throws Exception {
		mvc.createdLogin(uri, loginInput);
	}
}