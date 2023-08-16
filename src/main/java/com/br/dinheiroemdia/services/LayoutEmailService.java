package com.br.dinheiroemdia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.dinheiroemdia.entities.LayoutEmailEntity;
import com.br.dinheiroemdia.exceptions.NotFoundBussinessException;
import com.br.dinheiroemdia.repositories.LayoutEmailRepository;

import jakarta.transaction.Transactional;

@Service
public class LayoutEmailService {

	@Autowired
	private LayoutEmailRepository layoutEmailRepository;

	public boolean existRedefinePassword() {
		Optional<LayoutEmailEntity> layoutEmailEntity = layoutEmailRepository.findByName("Redefinir Senha");
		if (layoutEmailEntity.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public void register(LayoutEmailEntity layoutEmailEntity) {
		layoutEmailRepository.save(layoutEmailEntity);
	}

	public LayoutEmailEntity findByName(String name) {
		return layoutEmailRepository.findByName(name)
				.orElseThrow(() -> new NotFoundBussinessException("Layout " + name + " não encontrado"));
	}
}
