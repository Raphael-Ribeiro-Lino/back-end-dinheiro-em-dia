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

import com.br.dinheiroemdia.configs.ControllerConfig;
import com.br.dinheiroemdia.dto.inputs.EmailRedefinePasswordInput;
import com.br.dinheiroemdia.dto.inputs.RedefinePasswordInput;
import com.br.dinheiroemdia.utils.MyMvcMock;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class RedefinePasswordControllerTest {

	@Autowired
	private MyMvcMock mvc;

	private String uri;
	private String uriSendEmail;

	private EmailRedefinePasswordInput emailRedefinePasswordInput;
	private RedefinePasswordInput redefinePasswordInput;

	@BeforeEach
	void setup() throws Exception {
		this.uri = ControllerConfig.PRE_URL + "/user/redefine-password/";
		this.uriSendEmail = "/api-dinheiro-em-dia/user/redefine-password";

		this.emailRedefinePasswordInput = new EmailRedefinePasswordInput();
		this.emailRedefinePasswordInput.setEmail("raphar.lino@gmail.com");

		this.redefinePasswordInput = new RedefinePasswordInput();
		this.redefinePasswordInput.setPassword("@Spikezera47");
		this.redefinePasswordInput.setRepeatPassword("@Spikezera47");
	}

	@Test
	void quando_EnviarEmailParaRedefinicaoSemEmail_entao_RetornaErroEmailObrigatorio() throws Exception {
		this.emailRedefinePasswordInput.setEmail("");
		ResultActions result = mvc.createdWithBadRequest(this.uriSendEmail, emailRedefinePasswordInput);
		result.andExpect(jsonPath("$.campos.[?(@.message == 'O e-mail é obrigatório')]").exists());
	}

	@Test
	void quando_EnviarEmailParaRedefinicaoEmailNulo_entao_RetornaErroEmailObrigatorio() throws Exception {
		this.emailRedefinePasswordInput.setEmail(null);
		ResultActions result = mvc.createdWithBadRequest(this.uriSendEmail, emailRedefinePasswordInput);
		result.andExpect(jsonPath("$.campos.[?(@.message == 'O e-mail é obrigatório')]").exists());
	}

	@Test
	void quando_EnviarEmailParaRedefinicaoEmailNaoCadastrado_entao_RetornaErroUsuarioNaoEncontrado() throws Exception {
		this.emailRedefinePasswordInput.setEmail("raphar2.lino@gmail.com");
		ResultActions result = mvc.createdWithNotFound(this.uriSendEmail, emailRedefinePasswordInput);
		result.andExpect(jsonPath("$.[?(@.message == 'Usuário não encontrado')]").exists());
	}

	@Test
	void quando_RedefinirSenhaComHashInvalido_entao_RetornaErroHashNaoEncontrado() throws Exception {
		this.uri += "0";
		ResultActions result = mvc.updateWithNotFound(this.uri, this.redefinePasswordInput);
		result.andExpect(jsonPath("[?($.message == 'Hash não encontrado')]").exists());
	}

	@Test
	void quando_RedefinirSenhaSemSenha_entao_RetornaSenhaObrigatoria() throws Exception {
		this.redefinePasswordInput.setPassword("");
		ResultActions result = mvc.updateWithBadRequest(uri + "0", this.redefinePasswordInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'A senha é obrigatória')]").exists());
	}

	@Test
	void quando_RedefinirSenhaSenhaNula_entao_RetornaSenhaObrigatoria() throws Exception {
		this.redefinePasswordInput.setPassword(null);
		ResultActions result = mvc.updateWithBadRequest(uri + "0", this.redefinePasswordInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'A senha é obrigatória')]").exists());
	}

	@Test
	void quando_RedefinirSenhaTamanhoMaximoInvalido_Entao_RetornaErroSenhaTamanhoInvalido() throws Exception {
		this.redefinePasswordInput.setPassword(
				"exemplo321abcdefghijklmnopqrstuvwxyz12345adsadasasdasdasdasdasdasdsadasdasm,jdkajsdlkajslkdlkassajdasjdlkajdklsjdklsjalkdjaskldjklasjdklasdjklasjdklasjdkasjdklasjdklasjdklasjdklasjdjdjdjjddjjdjdjdjsj67890@example321abcdefghijklmnopqrstuvwxyz1234567890.com.br");
		ResultActions result = mvc.updateWithBadRequest(uri + "0", redefinePasswordInput);
		result.andExpect(jsonPath(
				"$.campos[?(@.message == 'A senha deve ter no mínimo 8 caracteres e no máximo 255 caracteres')]")
				.exists());
	}

	@Test
	void quando_RedefinirSenhaTamanhoMinimoInvalido_Entao_RetornaErroSenhaTamanhoInvalido() throws Exception {
		this.redefinePasswordInput.setPassword("@Rapha1");
		ResultActions result = mvc.updateWithBadRequest(uri + "0", redefinePasswordInput);
		result.andExpect(jsonPath(
				"$.campos[?(@.message == 'A senha deve ter no mínimo 8 caracteres e no máximo 255 caracteres')]")
				.exists());
	}

	@Test
	void quando_RedefinirSenhaSemRepetirSenha_Entao_RetornaErroRepetirSenhaObrigatorio() throws Exception {
		this.redefinePasswordInput.setRepeatPassword("");
		ResultActions result = mvc.updateWithBadRequest(uri + "0", redefinePasswordInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'Repetir a senha é obrigatório')]").exists());
	}

	@Test
	void quando_RedefinirSenhaRepetirSenhaNula_Entao_RetornaErroRepetirSenhaObrigatorio() throws Exception {
		this.redefinePasswordInput.setRepeatPassword(null);
		ResultActions result = mvc.updateWithBadRequest(uri + "0", redefinePasswordInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'Repetir a senha é obrigatório')]").exists());
	}

	@Test
	void quando_RedefinirSenhaRepetirSenhaTamanhoMaximoInvalido_Entao_RetornaErroRepetirSenhaTamanhoInvalido()
			throws Exception {
		this.redefinePasswordInput.setRepeatPassword(
				"exemplo321abcdefghijklmnopqrstuvwxyz12345adsadasasdasdasdasdasdasdsadasdasm,jdkajsdlkajslkdlkassajdasjdlkajdklsjdklsjalkdjaskldjklasjdklasdjklasjdklasjdkasjdklasjdklasjdklasjdklasjdjdjdjjddjjdjdjdjsj67890@example321abcdefghijklmnopqrstuvwxyz1234567890.com.br");
		ResultActions result = mvc.updateWithBadRequest(uri + "0", redefinePasswordInput);
		result.andExpect(jsonPath(
				"$.campos[?(@.message == 'A senha deve ter no mínimo 8 caracteres e no máximo 255 caracteres')]")
				.exists());
	}
	
	@Test
	void quando_RedefinirSenhaRepetirSenhaTamanhoMinimoInvalido_Entao_RetornaErroRepetirSenhaTamanhoInvalido()
			throws Exception {
		this.redefinePasswordInput.setRepeatPassword("@Rapha1");
		ResultActions result = mvc.updateWithBadRequest(uri + "0", redefinePasswordInput);
		result.andExpect(jsonPath(
				"$.campos[?(@.message == 'A senha deve ter no mínimo 8 caracteres e no máximo 255 caracteres')]")
				.exists());
	}
	
	@Test
	void quando_RedefinirSenhaSenhasDiferentes_Entao_RetornaErroSenhasDiferentes() throws Exception {
		this.redefinePasswordInput.setRepeatPassword("@Rapha15675675675");
		ResultActions result = mvc.updateWithBadRequest(uri + mvc.retornaHashRedefinirSenha(1l), redefinePasswordInput);
		result.andExpect(jsonPath("$.[?(@.message == 'As senhas não coincidem')]").exists());
	}
	
	@Test
	void quando_RedefinirSenha_entao_RetornaOk() throws Exception {
		mvc.update(uri + mvc.retornaHashRedefinirSenha(1l), this.redefinePasswordInput);
	}
}
