package com.br.dinheiroemdia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.dinheiroemdia.configs.ControllerConfig;
import com.br.dinheiroemdia.converts.UserConvert;
import com.br.dinheiroemdia.dto.inputs.EmailRedefinePasswordInput;
import com.br.dinheiroemdia.dto.inputs.RedefinePasswordInput;
import com.br.dinheiroemdia.dto.inputs.UserInput;
import com.br.dinheiroemdia.dto.outputs.UserOutput;
import com.br.dinheiroemdia.entities.UserEntity;
import com.br.dinheiroemdia.services.RedefinePasswordService;
import com.br.dinheiroemdia.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ControllerConfig.PRE_URL + "/user")
@CrossOrigin(origins = { "http://localhost", "http://localhost:4200" })
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserConvert userConvert;

	@Autowired
	private RedefinePasswordService redefinePasswordService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public UserOutput register(@RequestBody @Valid UserInput userInput) {
		userService.verifyPasswords(userInput.getPassword(), userInput.getRepeatPassword());
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

	@GetMapping("/{hash}/redefine-password")
	public void verifyHash(@PathVariable String hash) {
		redefinePasswordService.verifyHash(hash);
	}
	
	@PutMapping("/redefine-password/{hash}")
	@ResponseStatus(code = HttpStatus.OK)
	public void redefiniSenha(@RequestBody @Valid RedefinePasswordInput input, @PathVariable String hash) {
		UserEntity userEntity = redefinePasswordService.findUserByHash(hash);
		userService.redefinePassword(userEntity, input.getPassword(), input.getRepeatPassword(), hash);
	}
}
