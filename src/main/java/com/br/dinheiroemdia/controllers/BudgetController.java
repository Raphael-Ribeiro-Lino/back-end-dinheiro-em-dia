package com.br.dinheiroemdia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.dinheiroemdia.configs.ControllerConfig;
import com.br.dinheiroemdia.configs.securities.PodeAcessarSe;
import com.br.dinheiroemdia.converts.BudgetConvert;
import com.br.dinheiroemdia.dto.inputs.BudgetInput;
import com.br.dinheiroemdia.dto.outputs.BudgetOutput;
import com.br.dinheiroemdia.entities.BudgetEntity;
import com.br.dinheiroemdia.services.BudgetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ControllerConfig.PRE_URL + "/budgets")
@CrossOrigin(origins = { "http://localhost", "http://localhost:4200", "http://localhost:4200/*" })
public class BudgetController {
	
	@Autowired
	private BudgetService budgetService;
	
	@Autowired
	private BudgetConvert budgetConvert;
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	@PodeAcessarSe.EstaAutenticado
	public BudgetOutput register(@RequestBody @Valid BudgetInput budgetInput) {
		BudgetEntity budgetEntity = budgetConvert.inputToEntity(budgetInput);
		BudgetEntity budget = budgetService.register(budgetEntity);
		return budgetConvert.entityToOutput(budget);
	}
	
	@GetMapping("/{id}")
	@PodeAcessarSe.EstaAutenticado
	public BudgetOutput findById(@PathVariable Long id) {
		BudgetEntity budget = budgetService.findById(id);
		return budgetConvert.entityToOutput(budget);
	}

}
