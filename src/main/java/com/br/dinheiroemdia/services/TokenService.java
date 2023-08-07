package com.br.dinheiroemdia.services;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.br.dinheiroemdia.entities.UserEntity;
import com.br.dinheiroemdia.exceptions.UnauthorizedAccessBussinessException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenService {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserService userService;
	
	public String createToken(Authentication authentication) {
		UserEntity logged = userService.findByEmail(authentication.getName());
		Date data = new Date();
		
		JwtBuilder builder = Jwts.builder();
		builder.claim("id", logged.getId());
		builder.claim("email", logged.getEmail());
		builder.claim("name", logged.getName());
		builder.claim("profile", logged.getProfile());
		builder.setIssuer("API Dinheiro em Dia");
		builder.setIssuedAt(data);
		builder.setExpiration(new Date(data.getTime() + Long.parseLong("86400000")));
		builder.signWith(getSignInKey(), SignatureAlgorithm.HS256);
		return builder.compact();
	}
	
	public UserEntity getUserByToken() {
		Claims claims = extractClaims();
		Long id = Long.valueOf(claims.get("id").toString());
		return userService.findById(id);

	}
	
	public boolean canAccessAuthenticated() {
		Claims claims = extractClaims();
		if (claims != null) {
			return true;
		} else {
			return false;
		}
	}
	
	private Claims extractClaims() {
		String token = request.getHeader("Authorization");
		if(token == null) {
			throw new UnauthorizedAccessBussinessException("Acesso Negado!");
		}
		token = token.substring(7);
		try {
			return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
		} catch (Exception e) {
			throw new UnauthorizedAccessBussinessException("Token inválido!");
		}
	}
	
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode("2D4A614E645267556B58703273357638792F423F4528482B4D6250655368566D");
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
