package com.br.dinheiroemdia.controllers.budget;

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
import com.br.dinheiroemdia.utils.MyMvcMock;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class FindByIdBudgetControllerTest {

	@Autowired
	private MyMvcMock mvc;
	
	private String uri;
	private String token;
	
	private BudgetInput budgetInput;
	
	@BeforeEach
	void setUp() throws Exception {
		this.uri = ControllerConfig.PRE_URL + "/budgets/1";
		this.token = mvc.returnTokenAdm().getToken();
		
		this.budgetInput = new BudgetInput();
		this.budgetInput.setYearMonth(YearMonth.of(2023, 2));
		this.budgetInput.setPlannedBudget(BigDecimal.valueOf(3000.00));
		
		mvc.createWithToken(ControllerConfig.PRE_URL + "/budgets", token, budgetInput);
	}
	
	@Test
	void quando_buscarOrcamentoPorIdSemToken_Entao_RetornaErroAcessoNegado() throws Exception {
		ResultActions result = mvc.findWithUnauthorized(uri, budgetInput);
		result.andExpect(jsonPath("$.[?(@.message == 'Acesso Negado!')]").exists());
	}
	
	@Test
	void quando_buscarOrcamentoPorIdTokenInvalido_Entao_RetornaErroTokenInvalido() throws Exception {
		ResultActions result = mvc.findWithUnauthorized(uri, token+1, budgetInput);
		result.andExpect(jsonPath("$.[?(@.message == 'Token inválido!')]").exists());
	}
	
	@Test
	void quando_buscarOrcamentoPorId_Entao_RetornaOk() throws Exception {
		ResultActions result = mvc.findWithToken(uri, token, budgetInput);
		result.andExpect(jsonPath("id").exists());
		result.andExpect(jsonPath("yearMonth").value(budgetInput.getYearMonth().toString()));
		result.andExpect(jsonPath("totalIncome").value(0));
		result.andExpect(jsonPath("totalExpense").value(0));
		result.andExpect(jsonPath("plannedBudget").value(budgetInput.getPlannedBudget()));
		result.andExpect(jsonPath("actualBudget").value(0));
	}
}
