package com.br.dinheiroemdia.converts;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConvert {

	@Autowired
	private ModelMapper modelMapper;
}
