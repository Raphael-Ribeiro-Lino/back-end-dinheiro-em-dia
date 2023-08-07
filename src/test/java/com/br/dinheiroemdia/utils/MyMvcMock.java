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
	
	public ResultActions autenticated(String uri, Object objeto) throws Exception {
		return sendPost(uri, objeto).andExpect(status().isOk());
	}
	
	private ResultActions sendPost(String uri, Object objeto) throws Exception {
		return mvc.perform(post(uri).content(JSON.asJsonString(objeto)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
	}
	
	public ResultActions createdWithBadRequest(String token, String uri, Object objeto) throws Exception {
		return sendPost(token, uri, objeto).andExpect(status().isBadRequest());
	}
	
	private ResultActions sendPost(String token, String uri, Object objeto) throws Exception {
		return mvc.perform(post(uri).header("authorization", "Bearer " + token).content(JSON.asJsonString(objeto))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}
	
	public ResultActions createdWithBadRequest(String uri, Object objeto) throws Exception {
		return sendPost(uri, objeto).andExpect(status().isBadRequest());
	}
	
	public ResultActions createdLogin(String uri, Object objeto) throws Exception {
		return sendPost(uri, objeto).andExpect(status().isOk());
	}
}
