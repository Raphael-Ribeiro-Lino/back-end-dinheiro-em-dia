package com.br.dinheiroemdia.enums;

import org.springframework.security.core.GrantedAuthority;

public enum ProfileEnum implements GrantedAuthority{
	ADMIN, USER;

	@Override
	public String getAuthority() {
		return name();
	}
}
