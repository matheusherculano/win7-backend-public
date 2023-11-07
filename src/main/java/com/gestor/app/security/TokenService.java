package com.gestor.app.security;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gestor.app.DTO.LoginDTO;
import com.gestor.app.model.Usuario;
import com.gestor.app.service.UsuarioService;

@Service
public class TokenService {
	@Autowired
	private UsuarioService usuarioService;
	
	private static final Long EXPIRATION_TIME = 240l;
	
	public String login(LoginDTO loginDTO) throws Exception {
		Usuario usuario = usuarioService.findByUsuarioAndByAmbiente(loginDTO.usuario(), loginDTO.ambiente());
		if(usuario == null) {
			throw new Exception("Usuário não existente ou senha inválida"); 
		}
		
		Boolean senhaOk = passwordEncoder().matches(loginDTO.senha(), usuario.getSenha());
		
		if(senhaOk) {
			usuarioService.atualizarDataHoraUltimoAcesso(usuario);
			return gerarToken(loginDTO);
		}else {
			throw new Exception("Usuário não existente ou senha inválida"); 
		}
		
	}
	
	private BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private String gerarToken(LoginDTO loginDTO) {
		//subject usuario que esta logando
		return JWT.create().withIssuer("Win7").withSubject(loginDTO.usuario()).withClaim("ambiente", loginDTO.ambiente())
				.withExpiresAt(LocalDateTime.now().plusMinutes(EXPIRATION_TIME).toInstant(ZoneOffset.of("-03:00")))
				.sign(Algorithm.HMAC256("!#Xpt119@WiN7$#Cbm135$#@12"));
	}

	public String getSubject(String token) {
		return JWT.require(Algorithm.HMAC256("!#Xpt119@WiN7$#Cbm135$#@12"))
				.withIssuer("Win7")
				.build().verify(token).getSubject();
	}
	
	//retornando  nome do ambiente
	public String getAmbiente(String token) {
		return JWT.require(Algorithm.HMAC256("!#Xpt119@WiN7$#Cbm135$#@12"))
				.withIssuer("Win7")
				.build().verify(token).getClaim("ambiente").asString();
	}
}
