package com.br.dinheiroemdia.converts;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.dinheiroemdia.dto.inputs.UserInput;
import com.br.dinheiroemdia.dto.outputs.UserOutput;
import com.br.dinheiroemdia.entities.UserEntity;

import jakarta.validation.Valid;

@Component
public class UserConvert {

	@Autowired
	private ModelMapper modelMapper;

	public UserEntity inputToEntity(@Valid UserInput userInput) {
		return modelMapper.map(userInput, UserEntity.class);
	}

	public UserOutput entityToOutput(UserEntity user) {
		return modelMapper.map(user, UserOutput.class);
	}
}
