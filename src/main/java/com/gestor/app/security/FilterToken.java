package com.gestor.app.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.gestor.app.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class FilterToken extends OncePerRequestFilter{

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token;
		
//		response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		
		var authorizationHeader = request.getHeader("Authorization");
	
		if(authorizationHeader != null && !authorizationHeader.equals("Basic Og==")) {
			token = authorizationHeader.replace("Bearer", "").strip();
			var subject = this.tokenService.getSubject(token); //nome de usuario
			var ambiente = this.tokenService.getAmbiente(token);   //nome do ambiente
		
			var usuario = new UserPrincipal(usuarioService.findByUsuarioAndByAmbiente(subject, ambiente));
		
			var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			authentication.setDetails(ambiente.toLowerCase());
		
			//setando dados do usuário e as pemissões no contexto de login
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			/*   
			 * //pegando usuario autenticado 
			 * 
			 * import com.gestor.app.model.Usuario;
			 * 
			Usuario usuarioContex = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			*
			*/
		}
		
		filterChain.doFilter(request, response);
	}

}
