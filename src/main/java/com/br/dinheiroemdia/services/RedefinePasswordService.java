package com.br.dinheiroemdia.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.dinheiroemdia.entities.RedefinePasswordEntity;
import com.br.dinheiroemdia.entities.UserEntity;
import com.br.dinheiroemdia.exceptions.BadRequestBussinessException;
import com.br.dinheiroemdia.exceptions.NotFoundBussinessException;
import com.br.dinheiroemdia.repositories.RedefinePasswordRepository;

import jakarta.transaction.Transactional;

@Service
public class RedefinePasswordService {

	@Autowired
	private RedefinePasswordRepository redefinePasswordRepository;

	public Optional<RedefinePasswordEntity> findByUser(UserEntity userFound) {
		return redefinePasswordRepository.findByUser(userFound);
	}

	@Transactional
	public void delete(RedefinePasswordEntity redefinePasswordEntity) {
		redefinePasswordRepository.delete(redefinePasswordEntity);
	}

	@Transactional
	public RedefinePasswordEntity create(UserEntity userFound) {
		LocalDateTime expiration = LocalDateTime.now().plusMinutes(30);
		String hash = UUID.randomUUID().toString();
		RedefinePasswordEntity redefinePasswordEntity = new RedefinePasswordEntity();
		redefinePasswordEntity.setExpirationTime(expiration);
		redefinePasswordEntity.setHash(hash);
		redefinePasswordEntity.setUser(userFound);
		return redefinePasswordRepository.saveAndFlush(redefinePasswordEntity);
	}

	public void verifyHash(String hash) {
		RedefinePasswordEntity redefinePasswordEntity = redefinePasswordRepository.findByHash(hash)
				.orElseThrow(() -> new NotFoundBussinessException("Hash não encontrado"));
		if(redefinePasswordEntity.getExpirationTime().isBefore(LocalDateTime.now())) {
			throw new BadRequestBussinessException("Hash expirado! Envie o e-mail novamente para gerar outro hash");
		}
	}
}
