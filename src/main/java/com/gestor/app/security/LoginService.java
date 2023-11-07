package com.gestor.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.gestor.app.DTO.LoginDTO;

@Service
public class LoginService {

	@Autowired
	private TokenService tokenService;
	
	public String login(LoginDTO loginDTO) throws Exception {
		
		return tokenService.login(loginDTO);
	}
	
	public UserPrincipal getUserPrincal() {
		UserPrincipal usuarioContex = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		usuarioContex.clearPassword();
		return usuarioContex;
	}
}
