package com.br.dinheiroemdia.utils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@Component
public class MyMvcMock {
	@Autowired
	private MockMvc mvc;
	
	public ResultActions autenticated(String uri, Object object) throws Exception {
		return sendPost(uri, object).andExpect(status().isOk());
	}
	
	private ResultActions sendPost(String uri, Object object) throws Exception {
		return mvc.perform(post(uri).content(JSON.asJsonString(object)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
	}
	
	public ResultActions createdWithBadRequest(String uri, Object object) throws Exception {
		return sendPost(uri, object).andExpect(status().isBadRequest());
	}
	
	public ResultActions createdLogin(String uri, Object object) throws Exception {
		return sendPost(uri, object).andExpect(status().isOk());
	}

	public ResultActions create(String uri, Object object) throws Exception {
		return sendPost(uri, object).andExpect(status().isCreated());
	}
}