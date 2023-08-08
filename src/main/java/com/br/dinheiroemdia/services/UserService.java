package com.br.dinheiroemdia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
}
