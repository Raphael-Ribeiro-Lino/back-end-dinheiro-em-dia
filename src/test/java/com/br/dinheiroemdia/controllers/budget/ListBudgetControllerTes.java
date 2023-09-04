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
public class ListBudgetControllerTes {

	@Autowired
	private MyMvcMock mvc;
	
	private String uri;
	private String token;
	
	private BudgetInput budgetInput;
	private BudgetInput budgetInput2;
	private BudgetInput budgetInput3;
	private BudgetInput budgetInput4;
	private BudgetInput budgetInput5;
	private BudgetInput budgetInput6;
	private BudgetInput budgetInput7;
	private BudgetInput budgetInput8;
	private BudgetInput budgetInput9;
	private BudgetInput budgetInput10;
	
	@BeforeEach
	void setUp() throws Exception {
		this.uri = ControllerConfig.PRE_URL + "/budgets";
		this.token = mvc.returnTokenAdm().getToken();
		
		this.budgetInput = new BudgetInput();
		this.budgetInput.setYearMonth(YearMonth.of(2023, 1));
		this.budgetInput.setPlannedBudget(BigDecimal.valueOf(3000.00));
		
		this.budgetInput2 = new BudgetInput();
		this.budgetInput2.setYearMonth(YearMonth.of(2023, 2));
		this.budgetInput2.setPlannedBudget(BigDecimal.valueOf(3000.00));
		
		this.budgetInput3 = new BudgetInput();
		this.budgetInput3.setYearMonth(YearMonth.of(2023, 3));
		this.budgetInput3.setPlannedBudget(BigDecimal.valueOf(3000.00));
		
		this.budgetInput4 = new BudgetInput();
		this.budgetInput4.setYearMonth(YearMonth.of(2023, 4));
		this.budgetInput4.setPlannedBudget(BigDecimal.valueOf(3000.00));
		
		this.budgetInput5 = new BudgetInput();
		this.budgetInput5.setYearMonth(YearMonth.of(2023, 5));
		this.budgetInput5.setPlannedBudget(BigDecimal.valueOf(3000.00));
		
		this.budgetInput6 = new BudgetInput();
		this.budgetInput6.setYearMonth(YearMonth.of(2023, 6));
		this.budgetInput6.setPlannedBudget(BigDecimal.valueOf(3000.00));
		
		this.budgetInput7 = new BudgetInput();
		this.budgetInput7.setYearMonth(YearMonth.of(2023, 7));
		this.budgetInput7.setPlannedBudget(BigDecimal.valueOf(3000.00));
		
		this.budgetInput8 = new BudgetInput();
		this.budgetInput8.setYearMonth(YearMonth.of(2023, 8));
		this.budgetInput8.setPlannedBudget(BigDecimal.valueOf(3000.00));
		
		this.budgetInput9 = new BudgetInput();
		this.budgetInput9.setYearMonth(YearMonth.of(2023, 9));
		this.budgetInput9.setPlannedBudget(BigDecimal.valueOf(3000.00));
		
		this.budgetInput10 = new BudgetInput();
		this.budgetInput10.setYearMonth(YearMonth.of(2023, 10));
		this.budgetInput10.setPlannedBudget(BigDecimal.valueOf(3000.00));
		
		mvc.createWithToken(ControllerConfig.PRE_URL + "/budgets", token, budgetInput);
		mvc.createWithToken(ControllerConfig.PRE_URL + "/budgets", token, budgetInput2);
		mvc.createWithToken(ControllerConfig.PRE_URL + "/budgets", token, budgetInput3);
		mvc.createWithToken(ControllerConfig.PRE_URL + "/budgets", token, budgetInput4);
		mvc.createWithToken(ControllerConfig.PRE_URL + "/budgets", token, budgetInput5);
		mvc.createWithToken(ControllerConfig.PRE_URL + "/budgets", token, budgetInput6);
		mvc.createWithToken(ControllerConfig.PRE_URL + "/budgets", token, budgetInput7);
		mvc.createWithToken(ControllerConfig.PRE_URL + "/budgets", token, budgetInput8);
		mvc.createWithToken(ControllerConfig.PRE_URL + "/budgets", token, budgetInput9);
		mvc.createWithToken(ControllerConfig.PRE_URL + "/budgets", token, budgetInput10);
	}
	
	@Test
	void quando_listarOrcamentosSemToken_Entao_RetornaErroAcessoNegado() throws Exception {
		ResultActions result = mvc.findWithUnauthorized(uri);
		result.andExpect(jsonPath("$.[?(@.message == 'Acesso Negado!')]").exists());
	}
	
	@Test
	void quando_listarOrcamentosTokenInvalido_Entao_RetornaErroTokenInvalido() throws Exception {
		ResultActions result = mvc.findWithUnauthorized(uri, token+1);
		result.andExpect(jsonPath("$.[?(@.message == 'Token inválido!')]").exists());
	}
	
	@Test
	void quando_listarOrcamentos_Entao_RetornaOk() throws Exception {
		ResultActions result = mvc.findWithToken(uri, token);
		result.andExpect(jsonPath("content[0].id").exists());
		result.andExpect(jsonPath("content[0].yearMonth").value(budgetInput.getYearMonth().toString()));
		result.andExpect(jsonPath("content[0].totalIncome").value(0.0));
		result.andExpect(jsonPath("content[0].totalExpense").value(0.0));
		result.andExpect(jsonPath("content[0].plannedBudget").value(budgetInput.getPlannedBudget()));
		result.andExpect(jsonPath("content[0].actualBudget").value(0.0));

		result.andExpect(jsonPath("content[1].id").exists());
		result.andExpect(jsonPath("content[1].yearMonth").value(budgetInput2.getYearMonth().toString()));
		result.andExpect(jsonPath("content[1].totalIncome").value(0.0));
		result.andExpect(jsonPath("content[1].totalExpense").value(0.0));
		result.andExpect(jsonPath("content[1].plannedBudget").value(budgetInput2.getPlannedBudget()));
		result.andExpect(jsonPath("content[1].actualBudget").value(0.0));
		
		result.andExpect(jsonPath("content[2].id").exists());
		result.andExpect(jsonPath("content[2].yearMonth").value(budgetInput3.getYearMonth().toString()));
		result.andExpect(jsonPath("content[2].totalIncome").value(0.0));
		result.andExpect(jsonPath("content[2].totalExpense").value(0.0));
		result.andExpect(jsonPath("content[2].plannedBudget").value(budgetInput3.getPlannedBudget()));
		result.andExpect(jsonPath("content[2].actualBudget").value(0.0));
		
		result.andExpect(jsonPath("content[3].id").exists());
		result.andExpect(jsonPath("content[3].yearMonth").value(budgetInput4.getYearMonth().toString()));
		result.andExpect(jsonPath("content[3].totalIncome").value(0.0));
		result.andExpect(jsonPath("content[3].totalExpense").value(0.0));
		result.andExpect(jsonPath("content[3].plannedBudget").value(budgetInput4.getPlannedBudget()));
		result.andExpect(jsonPath("content[3].actualBudget").value(0.0));
		
		result.andExpect(jsonPath("content[4].id").exists());
		result.andExpect(jsonPath("content[4].yearMonth").value(budgetInput5.getYearMonth().toString()));
		result.andExpect(jsonPath("content[4].totalIncome").value(0.0));
		result.andExpect(jsonPath("content[4].totalExpense").value(0.0));
		result.andExpect(jsonPath("content[4].plannedBudget").value(budgetInput5.getPlannedBudget()));
		result.andExpect(jsonPath("content[4].actualBudget").value(0.0));
		
		result.andExpect(jsonPath("content[5].id").exists());
		result.andExpect(jsonPath("content[5].yearMonth").value(budgetInput6.getYearMonth().toString()));
		result.andExpect(jsonPath("content[5].totalIncome").value(0.0));
		result.andExpect(jsonPath("content[5].totalExpense").value(0.0));
		result.andExpect(jsonPath("content[5].plannedBudget").value(budgetInput6.getPlannedBudget()));
		result.andExpect(jsonPath("content[5].actualBudget").value(0.0));
		
		result.andExpect(jsonPath("content[6].id").exists());
		result.andExpect(jsonPath("content[6].yearMonth").value(budgetInput7.getYearMonth().toString()));
		result.andExpect(jsonPath("content[6].totalIncome").value(0.0));
		result.andExpect(jsonPath("content[6].totalExpense").value(0.0));
		result.andExpect(jsonPath("content[6].plannedBudget").value(budgetInput7.getPlannedBudget()));
		result.andExpect(jsonPath("content[6].actualBudget").value(0.0));
		
		result.andExpect(jsonPath("content[7].id").exists());
		result.andExpect(jsonPath("content[7].yearMonth").value(budgetInput8.getYearMonth().toString()));
		result.andExpect(jsonPath("content[7].totalIncome").value(0.0));
		result.andExpect(jsonPath("content[7].totalExpense").value(0.0));
		result.andExpect(jsonPath("content[7].plannedBudget").value(budgetInput8.getPlannedBudget()));
		result.andExpect(jsonPath("content[7].actualBudget").value(0.0));
		
		result.andExpect(jsonPath("content[8].id").exists());
		result.andExpect(jsonPath("content[8].yearMonth").value(budgetInput9.getYearMonth().toString()));
		result.andExpect(jsonPath("content[8].totalIncome").value(0.0));
		result.andExpect(jsonPath("content[8].totalExpense").value(0.0));
		result.andExpect(jsonPath("content[8].plannedBudget").value(budgetInput9.getPlannedBudget()));
		result.andExpect(jsonPath("content[8].actualBudget").value(0.0));
		
		result.andExpect(jsonPath("content[9].id").exists());
		result.andExpect(jsonPath("content[9].yearMonth").value(budgetInput10.getYearMonth().toString()));
		result.andExpect(jsonPath("content[9].totalIncome").value(0.0));
		result.andExpect(jsonPath("content[9].totalExpense").value(0.0));
		result.andExpect(jsonPath("content[9].plannedBudget").value(budgetInput10.getPlannedBudget()));
		result.andExpect(jsonPath("content[9].actualBudget").value(0.0));
	}
}
