package com.br.dinheiroemdia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.dinheiroemdia.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
}
