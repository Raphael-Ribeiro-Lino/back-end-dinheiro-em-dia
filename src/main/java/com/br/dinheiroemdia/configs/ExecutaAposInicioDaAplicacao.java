package com.br.dinheiroemdia.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import com.br.dinheiroemdia.entities.LayoutEmailEntity;
import com.br.dinheiroemdia.entities.UserEntity;
import com.br.dinheiroemdia.enums.ProfileEnum;
import com.br.dinheiroemdia.services.LayoutEmailService;
import com.br.dinheiroemdia.services.UserService;


@Configuration
public class ExecutaAposInicioDaAplicacao implements ApplicationRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private LayoutEmailService layoutEmailService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (!userService.existAdm()) {
			registerUserIfNeeded("Raphael Ribeiro Lino", "raphar.lino@gmail.com", "@Rapha12345678", ProfileEnum.ADMIN);
		}
		
		
		if(!layoutEmailService.existRedefinePassword()) {			
			registerLayoutIfNeeded("Redefinir Senha", "sistemadinheiroemdia@gmail.com",
					"Redefina a sua senha",
					"Foi solicitada a recuperação de senha do Dinheiro em Dia.<br /> <a href='" + "http://localhost:4200/redefine-password/"
					+ "{HASH}' target='_blank'>Clique aqui</a> para alterar.<br />"
					+ "Caso não tenha solicitado a alteração, ignore esta mensagem!");
		}
		
	}

	private void registerLayoutIfNeeded(String name, String sourceEmail,
			String subject, String body) {
		LayoutEmailEntity layoutEmailEntity = new LayoutEmailEntity(null, name, sourceEmail, subject, body);
		layoutEmailService.register(layoutEmailEntity);
	}

	private void registerUserIfNeeded(String name, String email, String password, ProfileEnum profile) {
		UserEntity userEntity = new UserEntity(null, name, email, password, profile);
		userService.register(userEntity);
	}

}
