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
import com.br.dinheiroemdia.converts.ExpenseConvert;
import com.br.dinheiroemdia.dto.inputs.ExpenseInput;
import com.br.dinheiroemdia.dto.outputs.ExpenseOutput;
import com.br.dinheiroemdia.entities.ExpenseEntity;
import com.br.dinheiroemdia.services.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ControllerConfig.PRE_URL + "/expenses")
@CrossOrigin(origins = { "http://localhost", "http://localhost:4200", "http://localhost:4200/*" })
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private ExpenseConvert expenseConvert;
	
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	@PodeAcessarSe.EstaAutenticado
	public ExpenseOutput register(@RequestBody @Valid ExpenseInput expenseInput) {
		ExpenseEntity expenseEntity = expenseConvert.inputToEntity(expenseInput);
		ExpenseEntity expense = expenseService.register(expenseEntity, expenseInput.getBudgetId());
		expenseService.updateBudget(expense);
		return expenseConvert.entityToOutput(expense);
	}
	
	@GetMapping("/{id}")
	@PodeAcessarSe.EstaAutenticado
	public ExpenseOutput findById(@PathVariable Long id) {
		ExpenseEntity expenseEntity = expenseService.findById(id);
		return expenseConvert.entityToOutput(expenseEntity);
	}
}
