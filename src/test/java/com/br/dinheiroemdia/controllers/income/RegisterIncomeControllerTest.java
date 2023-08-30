package com.br.dinheiroemdia.controllers.income;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.time.YearMonth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

import com.br.dinheiroemdia.configs.ControllerConfig;
import com.br.dinheiroemdia.dto.inputs.BudgetInput;
import com.br.dinheiroemdia.dto.inputs.IncomeInput;
import com.br.dinheiroemdia.utils.MyMvcMock;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class RegisterIncomeControllerTest {

	@Autowired
	private MyMvcMock mvc;

	private String uri;
	private String token;

	private BudgetInput budgetInput;
	private IncomeInput incomeInput;

	@BeforeEach
	void setUp() throws Exception {
		this.uri = ControllerConfig.PRE_URL + "/incomes";
		this.token = mvc.returnTokenAdm().getToken();

		this.budgetInput = new BudgetInput();
		this.budgetInput.setYearMonth(YearMonth.of(2023, 2));
		this.budgetInput.setPlannedBudget(BigDecimal.valueOf(3000.00));

		this.incomeInput = new IncomeInput();
		this.incomeInput.setName("Salário");
		this.incomeInput.setAmount(BigDecimal.valueOf(3000.00));
		this.incomeInput.setBudgetId(1l);

		mvc.createWithToken(ControllerConfig.PRE_URL + "/budgets", token, budgetInput);
	}

	@Test
	void quando_cadastrarReceitaSemNome_Entao_RetornaErroNomeObrigatorio() throws Exception {
		this.incomeInput.setName("");
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, incomeInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O nome é obrigatório')]").exists());
	}

	@Test
	void quando_cadastrarReceitaNomeNulo_Entao_RetornaErroNomeObrigatorio() throws Exception {
		this.incomeInput.setName(null);
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, incomeInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O nome é obrigatório')]").exists());
	}

	@Test
	void quando_cadastrarReceitaNomeTamanhoInvalido_Entao_RetornaErroNomeTamanhoInvalido() throws Exception {
		this.incomeInput.setName(
				"asdjkfsdfjsdjkhfjdkshfjdshfjkdshf jksdhfjdshfjkhsdkfjsdhjkfhsdjkfhsjdkfhjsdkfhsdjkhf sdfhfjdshfjkdshfjkdsh fsdjhfdjkshfjdkshfdjskhfjsdkhf jkdsfjdshfjklSHFIOEWHJOIHJFSDHJFHDSJFKHSD FDSHJKHFDSJKHF JKSDFHFKJDHSJFHDSJKFHDSJKFHJSDKHFUIWERHUIWERWEUIHRSFSDJKFHDSJ");
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, incomeInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O nome deve ter no máximo 255 caracteres')]").exists());
	}
	
	@Test
	void quando_cadastrarReceitaValorNulo_Entao_RetornaErroValorObrigatorio() throws Exception {
		this.incomeInput.setAmount(null);
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, incomeInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O valor é obrigatório')]").exists());
	}
	
	@Test
	void quando_cadastrarReceitaValorNegativo_Entao_RetornaErroValorNegativo() throws Exception {
		this.incomeInput.setAmount(BigDecimal.valueOf(-3000.00));
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, incomeInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O valor deve ser positivo')]").exists());
	}
	
	@Test
	void quando_cadastrarReceitaOrcamentoNulo_Entao_RetornaErroOrcamentoObrigatorio() throws Exception {
		this.incomeInput.setBudgetId(null);
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, incomeInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O orçamento é obrigatório')]").exists());
	}
	
	@Test
	void quando_cadastrarReceitaSemToken_Entao_RetornaErroAcessoNegado() throws Exception {
		ResultActions result = mvc.createWithUnauthorized(uri, incomeInput);
		result.andExpect(jsonPath("$.[?(@.message == 'Acesso Negado!')]").exists());
	}
	
	@Test
	void quando_cadastrarReceitaTokenInvalido_Entao_RetornaErroTokenInvalido() throws Exception {
		ResultActions result = mvc.createWithUnauthorized(uri, token+1, incomeInput);
		result.andExpect(jsonPath("$.[?(@.message == 'Token inválido!')]").exists());
	}
	
	@Test
	void quando_cadastrarReceita_Entao_RetornaCriado() throws Exception {
		ResultActions result = mvc.createWithToken(uri, token, incomeInput);
		result.andExpect(jsonPath("id").exists());
		result.andExpect(jsonPath("name").value(incomeInput.getName()));
		result.andExpect(jsonPath("amount").value(incomeInput.getAmount()));
	}
}
