package com.br.dinheiroemdia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.dinheiroemdia.configs.ControllerConfig;
import com.br.dinheiroemdia.configs.securities.PodeAcessarSe;
import com.br.dinheiroemdia.converts.IncomeConvert;
import com.br.dinheiroemdia.dto.inputs.IncomeInput;
import com.br.dinheiroemdia.dto.outputs.IncomeOutput;
import com.br.dinheiroemdia.entities.IncomeEntity;
import com.br.dinheiroemdia.services.IncomeCategoryService;
import com.br.dinheiroemdia.services.IncomeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ControllerConfig.PRE_URL + "/incomes")
@CrossOrigin(origins = { "http://localhost", "http://localhost:4200", "http://localhost:4200/*" })
public class IncomeController {

	@Autowired
	private IncomeService incomeService;

	@Autowired
	private IncomeConvert incomeConvert;
	
	@Autowired
	private IncomeCategoryService incomeCategoryService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	@PodeAcessarSe.EstaAutenticado
	public IncomeOutput register(@RequestBody @Valid IncomeInput incomeInput) {
		IncomeEntity incomeEntity = incomeConvert.inputToEntity(incomeInput);
		IncomeEntity income = incomeService.register(incomeEntity, incomeInput.getBudgetId());
		incomeService.updateBudget(income);
		return incomeConvert.entityToOutput(income);
	}

	@GetMapping("/{id}")
	@PodeAcessarSe.EstaAutenticado
	public IncomeOutput findById(@PathVariable Long id) {
		IncomeEntity incomeEntity = incomeService.findById(id);
		return incomeConvert.entityToOutput(incomeEntity);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@PodeAcessarSe.EstaAutenticado
	public void delete(@PathVariable Long id) {
		IncomeEntity incomeEntity = incomeService.findById(id);
		incomeCategoryService.deleteCategory(incomeEntity);
		incomeService.delete(incomeEntity);
		incomeService.updateBudget(incomeEntity);
	}
}
