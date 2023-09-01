package com.br.dinheiroemdia.controllers.expense;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import com.br.dinheiroemdia.dto.inputs.ExpenseInput;
import com.br.dinheiroemdia.enums.ValueTypeEnum;
import com.br.dinheiroemdia.utils.MyMvcMock;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class RegisterExpenseControllerTest {

	@Autowired
	private MyMvcMock mvc;

	private String uri;
	private String token;

	private BudgetInput budgetInput;
	private ExpenseInput expenseInput;
	
	@BeforeEach
	void setUp() throws Exception {
		this.uri = ControllerConfig.PRE_URL + "/expenses";
		this.token = mvc.returnTokenAdm().getToken();

		this.budgetInput = new BudgetInput();
		this.budgetInput.setYearMonth(YearMonth.of(2023, 2));
		this.budgetInput.setPlannedBudget(BigDecimal.valueOf(3000.00));

		this.expenseInput = new ExpenseInput();
		this.expenseInput.setName("Aluguel");
		this.expenseInput.setAmount(BigDecimal.valueOf(2000.00));
		this.expenseInput.setDate(LocalDateTime.now());
		this.expenseInput.setType(ValueTypeEnum.Fixo);
		this.expenseInput.setBudgetId(1l);

		mvc.createWithToken(ControllerConfig.PRE_URL + "/budgets", token, budgetInput);
	}
	
	@Test
	void quando_cadastrarDespesaSemNome_Entao_RetornaErroNomeObrigatorio() throws Exception {
		this.expenseInput.setName("");
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, expenseInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O nome é obrigatório')]").exists());
	}
	
	@Test
	void quando_cadastrarDespesaNomeNulo_Entao_RetornaErroNomeObrigatorio() throws Exception {
		this.expenseInput.setName(null);
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, expenseInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O nome é obrigatório')]").exists());
	}
	
	@Test
	void quando_cadastrarDespesaNomeTamanhoInvalido_Entao_RetornaErroNomeTamanhoInvalido() throws Exception {
		this.expenseInput.setName(
				"asdjkfsdfjsdjkhfjdkshfjdshfjkdshf jksdhfjdshfjkhsdkfjsdhjkfhsdjkfhsjdkfhjsdkfhsdjkhf sdfhfjdshfjkdshfjkdsh fsdjhfdjkshfjdkshfdjskhfjsdkhf jkdsfjdshfjklSHFIOEWHJOIHJFSDHJFHDSJFKHSD FDSHJKHFDSJKHF JKSDFHFKJDHSJFHDSJKFHDSJKFHJSDKHFUIWERHUIWERWEUIHRSFSDJKFHDSJ");
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, expenseInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O nome deve ter no máximo 255 caracteres')]").exists());
	}
	
	@Test
	void quando_cadastrarDespesaValorNulo_Entao_RetornaErroValorObrigatorio() throws Exception {
		this.expenseInput.setAmount(null);
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, expenseInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O valor é obrigatório')]").exists());
	}
	
	@Test
	void quando_cadastrarDespesaValorNegativo_Entao_RetornaErroValorNegativo() throws Exception {
		this.expenseInput.setAmount(BigDecimal.valueOf(-3000.00));
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, expenseInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O valor deve ser positivo')]").exists());
	}
	
	@Test
	void quando_cadastrarDespesaDataNula_Entao_RetornaErroDataObrigatorio() throws Exception {
		this.expenseInput.setDate(null);
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, expenseInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'A data é obrigatória')]").exists());
	}
	
	@Test
	void quando_cadastrarDespesaTipoNulo_Entao_RetornaErroTipoObrigatorio() throws Exception {
		this.expenseInput.setType(null);
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, expenseInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O tipo é obrigatório')]").exists());
	}
	
	@Test
	void quando_cadastrarDespesaOrcamentoNulo_Entao_RetornaErroOrcamentoObrigatorio() throws Exception {
		this.expenseInput.setBudgetId(null);
		ResultActions result = mvc.createdWithTokenBadRequest(uri, token, expenseInput);
		result.andExpect(jsonPath("$.campos[?(@.message == 'O orçamento é obrigatório')]").exists());
	}
	
	@Test
	void quando_cadastrarDespesaSemToken_Entao_RetornaErroAcessoNegado() throws Exception {
		ResultActions result = mvc.createWithUnauthorized(uri, expenseInput);
		result.andExpect(jsonPath("$.[?(@.message == 'Acesso Negado!')]").exists());
	}
	
	@Test
	void quando_cadastrarDespesaTokenInvalido_Entao_RetornaErroTokenInvalido() throws Exception {
		ResultActions result = mvc.createWithUnauthorized(uri, token+1, expenseInput);
		result.andExpect(jsonPath("$.[?(@.message == 'Token inválido!')]").exists());
	}
	
	@Test
	void quando_cadastrarDespesa_Entao_RetornaCriado() throws Exception {
		ResultActions result = mvc.createWithToken(uri, token, expenseInput);
		result.andExpect(jsonPath("id").exists());
		result.andExpect(jsonPath("name").value(expenseInput.getName()));
		result.andExpect(jsonPath("amount").value(expenseInput.getAmount()));
		result.andExpect(jsonPath("date").exists());
		result.andExpect(jsonPath("type").value(expenseInput.getType().toString()));
	}
}
