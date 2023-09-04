package com.br.dinheiroemdia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.dinheiroemdia.configs.ControllerConfig;
import com.br.dinheiroemdia.configs.securities.PodeAcessarSe;
import com.br.dinheiroemdia.converts.IncomeCategoryConvert;
import com.br.dinheiroemdia.dto.inputs.IncomeCategoryInput;
import com.br.dinheiroemdia.dto.outputs.IncomeCategoryOutput;
import com.br.dinheiroemdia.entities.IncomeCategoryEntity;
import com.br.dinheiroemdia.services.IncomeCategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ControllerConfig.PRE_URL + "/income-categories")
@CrossOrigin(origins = { "http://localhost", "http://localhost:4200", "http://localhost:4200/*" })
public class IncomeCategoryController {

	@Autowired
	private IncomeCategoryService incomeCategoryService;
	
	@Autowired
	private IncomeCategoryConvert incomeCategoryConvert;
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	@PodeAcessarSe.EstaAutenticado
	public IncomeCategoryOutput register(@RequestBody @Valid IncomeCategoryInput incomeCategoryInput) {
		incomeCategoryService.verifyCategory(incomeCategoryInput.getName(), incomeCategoryInput.getIncomeId());
		IncomeCategoryEntity incomeCategoryEntity = incomeCategoryConvert.inputToEntity(incomeCategoryInput);
		IncomeCategoryEntity incomeCategory = incomeCategoryService.register(incomeCategoryEntity, incomeCategoryInput.getIncomeId());
		return incomeCategoryConvert.entityToOutput(incomeCategory);
	}
	
	@GetMapping
	public Page<IncomeCategoryOutput> list(@PageableDefault(size = 10, sort = "name", direction = Direction.ASC) Pageable pagination){
		Page<IncomeCategoryEntity> incomeCategories = incomeCategoryService.list(pagination);
		return incomeCategoryConvert.pageEntityToOutput(incomeCategories);
	}
}
