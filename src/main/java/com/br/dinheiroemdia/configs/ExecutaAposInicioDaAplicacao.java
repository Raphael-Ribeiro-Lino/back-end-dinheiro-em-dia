package com.br.dinheiroemdia.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import com.br.dinheiroemdia.entities.UserEntity;
import com.br.dinheiroemdia.enums.ProfileEnum;
import com.br.dinheiroemdia.services.UserService;

@Configuration
public class ExecutaAposInicioDaAplicacao implements ApplicationRunner {

	@Autowired
	private UserService userService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (!userService.existAdm()) {
			registerUserIfNeeded("Raphael Ribeiro Lino", "raphar.lino@gmail.com", "@Rapha12345678", ProfileEnum.ADMIN);
		}
	}

	private void registerUserIfNeeded(String name, String email, String password, ProfileEnum profile) {
		UserEntity userEntity = new UserEntity(null, name, email, password, profile);
		userService.register(userEntity);
	}

}
