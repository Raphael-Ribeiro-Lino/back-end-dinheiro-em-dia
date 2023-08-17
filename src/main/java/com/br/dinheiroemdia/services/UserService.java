package com.br.dinheiroemdia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.dinheiroemdia.entities.LayoutEmailEntity;
import com.br.dinheiroemdia.entities.RedefinePasswordEntity;
import com.br.dinheiroemdia.entities.UserEntity;
import com.br.dinheiroemdia.enums.ProfileEnum;
import com.br.dinheiroemdia.exceptions.BadRequestBussinessException;
import com.br.dinheiroemdia.exceptions.NotFoundBussinessException;
import com.br.dinheiroemdia.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RedefinePasswordService redefinePasswordService;
	
	@Autowired
	private LayoutEmailService layoutEmailService;

	@Autowired
	private EmailService emailService;
	
	public boolean existAdm() {
		if(userRepository.findByProfile(ProfileEnum.ADMIN).isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	
	public void existUser(String email) {
		if(userRepository.findByEmail(email).isPresent()) {
			throw new BadRequestBussinessException("O endereço de e-mail já está registrado. Por favor, escolha um endereço de e-mail diferente ou faça login na sua conta existente");
		}
	}

	@Transactional
	public UserEntity register(UserEntity userEntity) {
		existUser(userEntity.getEmail());
		userEntity.setPassword(new BCryptPasswordEncoder().encode(userEntity.getPassword()));
		return userRepository.save(userEntity);
	}

	public UserEntity findByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundBussinessException("Usuário não encontrado"));
	}

	public UserEntity findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new NotFoundBussinessException("Usuário " + id + " não encontrado"));
	}

	public void verifyPasswords(String password, String repeatPassword) {
		if(!password.equals(repeatPassword)) {
			throw new BadRequestBussinessException("As senhas não coincidem");
		}
	}

	@Transactional
	public void sendEmailRedefinePassword(UserEntity userFound) {
		Optional<RedefinePasswordEntity> redefinePasswordEntity = redefinePasswordService.findByUser(userFound);
		RedefinePasswordEntity redefinePassword;
		if(redefinePasswordEntity.isPresent()) {
			redefinePasswordService.delete(redefinePasswordEntity.get());
			redefinePassword = redefinePasswordService.create(userFound);
		}else {
			redefinePassword = redefinePasswordService.create(userFound);
		}
		
		LayoutEmailEntity layoutEmailRedifinirSenha = layoutEmailService.findByName("Redefinir Senha");
		
		String body = redefineBody(layoutEmailRedifinirSenha.getBody(), redefinePassword);
		
		LayoutEmailEntity layoutEmailEntity = new LayoutEmailEntity(null, layoutEmailRedifinirSenha.getName(),
				layoutEmailRedifinirSenha.getSourceEmail(), layoutEmailRedifinirSenha.getSubject(), body);
		
		emailService.sendEmail(userFound.getEmail(), layoutEmailEntity);
	}

	private String redefineBody(String body, RedefinePasswordEntity redefinePassword) {
		return body.replace("{HASH}", redefinePassword.getHash());
	}

	@Transactional
	public void redefinePassword(UserEntity userEntity, String password, String repeatPassword, String hash) {
		redefinePasswordService.verifyHash(hash);
		verifyPasswords(password, repeatPassword);
		Optional<RedefinePasswordEntity> redefinePasswordEntity = redefinePasswordService.findByUser(userEntity);
		userEntity.setPassword(new BCryptPasswordEncoder().encode(password));
		userRepository.save(userEntity);
		redefinePasswordService.delete(redefinePasswordEntity.get());
	}
}
