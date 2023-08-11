package com.br.dinheiroemdia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.dinheiroemdia.repositories.RedefinePasswordRepository;

@Service
public class RedefinePasswordService {

	@Autowired
	private RedefinePasswordRepository redefinePasswordRepository;
}
