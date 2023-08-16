package com.br.dinheiroemdia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.dinheiroemdia.configs.ControllerConfig;
import com.br.dinheiroemdia.converts.UserConvert;
import com.br.dinheiroemdia.dto.inputs.EmailRedefinePasswordInput;
import com.br.dinheiroemdia.dto.inputs.UserInput;
import com.br.dinheiroemdia.dto.outputs.UserOutput;
import com.br.dinheiroemdia.entities.UserEntity;
import com.br.dinheiroemdia.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ControllerConfig.PRE_URL + "/user")
@CrossOrigin(origins = {"http://localhost", "http://localhost:4200" })
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserConvert userConvert;
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public UserOutput register(@RequestBody @Valid UserInput userInput) {
		userService.verifyPasswords(userInput);
		UserEntity userEntity = userConvert.inputToEntity(userInput);
		UserEntity user = userService.register(userEntity);
		return userConvert.entityToOutput(user);
	}
	
	@PostMapping("/redefine-password")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void redefinePassword(@RequestBody @Valid EmailRedefinePasswordInput emailRedefinePasswordInput) {
		UserEntity userFound = userService.findByEmail(emailRedefinePasswordInput.getEmail());
		userService.sendEmailRedefinePassword(userFound);
	}
}
