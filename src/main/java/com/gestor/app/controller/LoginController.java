package com.gestor.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.app.DTO.LoginDTO;
import com.gestor.app.security.LoginService;
import com.gestor.app.security.UserPrincipal;

@RestController
@CrossOrigin(origins = {"http://localhost:4200/", "https://localhost:4200/"})
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> save(@RequestBody LoginDTO dto) {

		String token;
		try {
			token = loginService.login(dto);
			return new ResponseEntity<String>(token, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

	@RequestMapping(value = "/userprincipal", method = RequestMethod.GET)
	public ResponseEntity<UserPrincipal> getRoles() {
			UserPrincipal usuarioContex = loginService.getUserPrincal();
			return new ResponseEntity<UserPrincipal>(usuarioContex, HttpStatus.OK);
	}

}
