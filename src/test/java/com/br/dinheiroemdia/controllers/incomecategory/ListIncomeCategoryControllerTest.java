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
public class ListIncomeCategoryControllerTest {

	@Autowired
	private MyMvcMock mvc;

	private String uri;
	private String token;

	private BudgetInput budgetInput;
	private IncomeInput incomeInput;
	private IncomeInput incomeInput2;
	private IncomeCategoryInput incomeCategoryInput;
	private IncomeCategoryInput incomeCategoryInput2;
	
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
		
		this.incomeInput2 = new IncomeInput();
		this.incomeInput2.setName("Aluguel Casa");
		this.incomeInput2.setAmount(BigDecimal.valueOf(3000.00));
		this.incomeInput2.setBudgetId(1l);
		
		this.incomeCategoryInput = new IncomeCategoryInput();
		this.incomeCategoryInput.setName("Alugueis");
		this.incomeCategoryInput.setIncomeId(1l);
		
		this.incomeCategoryInput2 = new IncomeCategoryInput();
		this.incomeCategoryInput2.setName("Investimentos");
		this.incomeCategoryInput2.setIncomeId(2l);

		mvc.createWithToken(ControllerConfig.PRE_URL + "/budgets", token, budgetInput);
		mvc.createWithToken(ControllerConfig.PRE_URL + "/incomes", token, incomeInput);
		mvc.createWithToken(ControllerConfig.PRE_URL + "/incomes", token, incomeInput2);
		mvc.createWithToken(ControllerConfig.PRE_URL + "/income-categories", token, incomeCategoryInput); 
		mvc.createWithToken(ControllerConfig.PRE_URL + "/income-categories", token, incomeCategoryInput2); 
	}
	
	@Test
	void quando_listarCategoriasReceitaSemToken_Entao_RetornaErroAcessoNegado() throws Exception {
		ResultActions result = mvc.findWithUnauthorized(uri);
		result.andExpect(jsonPath("$.[?(@.message == 'Acesso Negado!')]").exists());
	}
	
	@Test
	void quando_listarCategoriasReceitaTokenInvalido_Entao_RetornaErroTokenInvalido() throws Exception {
		ResultActions result = mvc.findWithUnauthorized(uri, token+1);
		result.andExpect(jsonPath("$.[?(@.message == 'Token inválido!')]").exists());
	}
	
	@Test
	void quando_listarCategoriasReceita_Entao_RetornaOk() throws Exception {
		ResultActions result = mvc.findWithToken(uri, token);
		result.andExpect(jsonPath("content[0].id").exists());
		result.andExpect(jsonPath("content[0].name").value(incomeCategoryInput.getName()));
		result.andExpect(jsonPath("content[0].income.id").value(1l));
		result.andExpect(jsonPath("content[0].income.name").value(incomeInput.getName()));
		result.andExpect(jsonPath("content[0].income.amount").value(incomeInput.getAmount()));
		
		result.andExpect(jsonPath("content[1].id").exists());
		result.andExpect(jsonPath("content[1].name").value(incomeCategoryInput2.getName()));
		result.andExpect(jsonPath("content[1].income.id").value(2l));
		result.andExpect(jsonPath("content[1].income.name").value(incomeInput2.getName()));
		result.andExpect(jsonPath("content[1].income.amount").value(incomeInput2.getAmount()));
	}
}
