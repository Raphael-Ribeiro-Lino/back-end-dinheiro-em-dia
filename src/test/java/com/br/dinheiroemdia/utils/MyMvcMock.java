package com.br.dinheiroemdia.utils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.br.dinheiroemdia.configs.ControllerConfig;
import com.br.dinheiroemdia.dto.inputs.LoginInput;
import com.br.dinheiroemdia.dto.outputs.TokenOutput;
import com.br.dinheiroemdia.entities.RedefinePasswordEntity;
import com.br.dinheiroemdia.entities.UserEntity;
import com.br.dinheiroemdia.services.RedefinePasswordService;
import com.br.dinheiroemdia.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class MyMvcMock {
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RedefinePasswordService redefinePasswordService;
	
	public ResultActions autenticated(String uri, Object object) throws Exception {
		return sendPost(uri, object).andExpect(status().isOk());
	}
	
	private ResultActions sendPost(String uri, Object object) throws Exception {
		return mvc.perform(post(uri).content(JSON.asJsonString(object)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
	}
	
//	private ResultActions sendPost(String token, String uri) throws Exception {
//		return mvc.perform(post(uri).header("authorization", "Bearer "+ token)
//				.accept(MediaType.APPLICATION_JSON));
//	}
	
	private ResultActions sendPost(String uri, String token, Object objeto) throws Exception {
		return mvc.perform(post(uri).header("authorization", "Bearer " + token).content(JSON.asJsonString(objeto))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}
	
	private ResultActions sendPut(String uri, Object objeto) throws Exception {
		return mvc.perform(put(uri).content(JSON.asJsonString(objeto)).contentType(MediaType.APPLICATION_JSON)
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

	public ResultActions createdWithNotFound(String uri, Object object) throws Exception {
		return sendPost(uri, object).andExpect(status().isNotFound());
	}

	public ResultActions updateWithNotFound(String uri, Object object) throws Exception {
		return sendPut(uri, object).andExpect(status().isNotFound());
	}

	public ResultActions updateWithBadRequest(String uri, Object object) throws Exception {
		return sendPut(uri, object).andExpect(status().isBadRequest());
	}

	public String retornaHashRedefinirSenha(Long id) throws Exception {
		UserEntity user = userService.findById(id);
		RedefinePasswordEntity redefinePasswordEntity = redefinePasswordService.create(user);
		return redefinePasswordEntity.getHash();
	}

	public ResultActions update(String uri, Object object) throws Exception {
		return sendPut(uri, object).andExpect(status().isOk());
	}

	public TokenOutput returnTokenAdm() throws Exception {
		LoginInput login = new LoginInput();
		login.setEmail("raphar.lino@gmail.com");
		login.setPassword("@Rapha12345678");
		MvcResult andReturn = this.autenticated(ControllerConfig.PRE_URL + "/auth", login).andReturn();
		String contentAsString = andReturn.getResponse().getContentAsString();
		TokenOutput token = new ObjectMapper().readValue(contentAsString, TokenOutput.class);
		return token;
	}

	public ResultActions createWithUnauthorized(String uri, Object object) throws Exception {
		return sendPost(uri, object).andExpect(status().isUnauthorized());
	}

	public ResultActions createWithUnauthorized(String uri, String token, Object object) throws Exception {
		return sendPost(uri, token, object).andExpect(status().isUnauthorized());
	}

	public ResultActions createWithToken(String uri, String token, Object object) throws Exception {
		return sendPost(uri, token, object).andExpect(status().isCreated());
	}

	public ResultActions createdWithTokenBadRequest(String uri, String token, Object object) throws Exception {
		return sendPost(uri, token, object).andExpect(status().isBadRequest());
	}

	public ResultActions findWithUnauthorized(String uri, Object object) throws Exception {
		return sendGet(uri, object).andExpect(status().isUnauthorized());
	}

	private ResultActions sendGet(String uri, Object object) throws Exception {
		return mvc.perform(get(uri).content(JSON.asJsonString(object)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
	}

	public ResultActions findWithUnauthorized(String uri, String token, Object object) throws Exception {
		return sendGet(uri, token, object).andExpect(status().isUnauthorized());
	}

	private ResultActions sendGet(String uri, String token, Object object) throws Exception {
		return mvc.perform(get(uri).header("authorization", "Bearer " + token).content(JSON.asJsonString(object))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}

	public ResultActions findWithToken(String uri, String token, Object object) throws Exception {
		return sendGet(uri, token, object).andExpect(status().isOk());
	}

}