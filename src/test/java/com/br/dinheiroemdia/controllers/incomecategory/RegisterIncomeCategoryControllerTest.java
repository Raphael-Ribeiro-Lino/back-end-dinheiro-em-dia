package com.br.dinheiroemdia.controllers.incomecategory;

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
import com.br.dinheiroemdia.dto.inputs.IncomeCategoryInput;
import com.br.dinheiroemdia.dto.inputs.IncomeInput;
import com.br.dinheiroemdia.utils.MyMvcMock;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class RegisterIncomeCategoryControllerTest {

	@Autowired
	private MyMvcMock mvc;

	private String uri;
	private String token;

	private BudgetInput budgetInput;
	private IncomeInput incomeInput;
	private IncomeCategoryInput incomeCategoryInput;
	
	@BeforeEach
	void setUp() throws Exception {
		this.uri = ControllerConfig.PRE_URL + "/income-categories";
		this.token = mvc.returnTokenAdm().getToken();

		this.budgetInput = new BudgetInput();
		this.budgetInput.setYearMonth(YearMonth.of(2023, 2));
		this.budgetInput.setPlannedBudget(BigDecimal.valueOf(3000.00));

		this.incomeInput = new IncomeInput();
		this.incomeInput.setName("Salário");
		this.incomeInput.setAmount(BigDecimal.valueOf(3000.00));
		this.incomeInput.setBudgetId(1l);
		
		this.incomeCategoryInput = new IncomeCategoryInput();
		this.incomeCategoryInput.setName("Investimentos");
		this.incomeCategoryInput.setIncomeId(1l);

		mvc.createWithToken(ControllerConfig.PRE_URL + "/budgets", token, budgetInput);
		mvc.createWithToken(ControllerConfig.PRE_URL + "/incomes", token, incomeInput);
	}
	
	@Test
	void quando_cadastrarCategoriaReceitaSemNome_Entao_RetornaErroNomeObrigatorio() throws Exception {
		this.incomeCategoryInput.setName("");
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, incomeCategoryInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O nome é obrigatório')]").exists());
	}
	
	@Test
	void quando_cadastrarCategoriaReceitaNomeNulo_Entao_RetornaErroNomeObrigatorio() throws Exception {
		this.incomeCategoryInput.setName(null);
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, incomeCategoryInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O nome é obrigatório')]").exists());
	}
	
	@Test
	void quando_cadastrarCategoriaReceitaNomeTamanhoInvalido_Entao_RetornaErroNomeTamanhoInvalido() throws Exception {
		this.incomeCategoryInput.setName(
				"asdjkfsdfjsdjkhfjdkshfjdshfjkdshf jksdhfjdshfjkhsdkfjsdhjkfhsdjkfhsjdkfhjsdkfhsdjkhf sdfhfjdshfjkdshfjkdsh fsdjhfdjkshfjdkshfdjskhfjsdkhf jkdsfjdshfjklSHFIOEWHJOIHJFSDHJFHDSJFKHSD FDSHJKHFDSJKHF JKSDFHFKJDHSJFHDSJKFHDSJKFHJSDKHFUIWERHUIWERWEUIHRSFSDJKFHDSJ");
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, incomeCategoryInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O nome deve ter no máximo 255 caracteres')]").exists());
	}
	
	@Test
	void quando_cadastrarCategoriaReceitaIdReceitaNulo_Entao_RetornaErroOrcamentoObrigatorio() throws Exception {
		this.incomeCategoryInput.setIncomeId(null);
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, incomeCategoryInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'A receita é obrigatória')]").exists());
	}
	
	@Test
	void quando_cadastrarCategoriaReceitaSemToken_Entao_RetornaErroAcessoNegado() throws Exception {
		ResultActions result = mvc.createWithUnauthorized(uri, incomeCategoryInput);
		result.andExpect(jsonPath("$.[?(@.message == 'Acesso Negado!')]").exists());
	}
	
	@Test
	void quando_cadastrarCategoriaReceitaTokenInvalido_Entao_RetornaErroTokenInvalido() throws Exception {
		ResultActions result = mvc.createWithUnauthorized(uri, token+1, incomeCategoryInput);
		result.andExpect(jsonPath("$.[?(@.message == 'Token inválido!')]").exists());
	}
	
	@Test
	void quando_cadastrarCategoriaReceita_Entao_RetornaCriado() throws Exception {
		ResultActions result = mvc.createWithToken(uri, token, incomeCategoryInput);
		result.andExpect(jsonPath("id").exists());
		result.andExpect(jsonPath("name").value(incomeCategoryInput.getName()));
		result.andExpect(jsonPath("income.id").value(1l));
		result.andExpect(jsonPath("income.name").value(incomeInput.getName()));
		result.andExpect(jsonPath("income.amount").value(incomeInput.getAmount()));
	}
}
