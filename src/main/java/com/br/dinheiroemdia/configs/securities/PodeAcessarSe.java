package com.br.dinheiroemdia.configs.securities;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface PodeAcessarSe {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessAuthenticated()")
	public @interface EstaAutenticado {

	}

}
