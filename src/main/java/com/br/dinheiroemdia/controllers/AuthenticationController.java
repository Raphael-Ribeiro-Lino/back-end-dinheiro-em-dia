package com.br.dinheiroemdia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.dinheiroemdia.configs.ControllerConfig;
import com.br.dinheiroemdia.dto.inputs.LoginInput;
import com.br.dinheiroemdia.dto.outputs.TokenOutput;
import com.br.dinheiroemdia.exceptions.BadRequestBussinessException;
import com.br.dinheiroemdia.services.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ControllerConfig.PRE_URL + "/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public TokenOutput authenticate(@RequestBody @Valid LoginInput login) {
		UsernamePasswordAuthenticationToken loginData = login.converter();
		try {
			Authentication authentication = (Authentication) authenticationManager.authenticate(loginData);
			String token = tokenService.createToken(authentication);
			return new TokenOutput(token, "Bearer");
		} catch (AuthenticationException e) {
			throw new BadRequestBussinessException("E-mail ou Senha Inválida");
		}
	}
	
}
