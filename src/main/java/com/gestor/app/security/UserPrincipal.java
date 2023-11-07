package com.gestor.app.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gestor.app.model.Role;
import com.gestor.app.model.Usuario;

public class UserPrincipal implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6270973984841812866L;
	private Long id;
	private String username;
	private String name;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(Usuario usuario) {
		this.id = usuario.getId();
		this.username = usuario.getLogin();
		this.name = usuario.getNome();
		this.password = usuario.getSenha();

		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		List<Role> userRoles = new ArrayList<>(usuario.getRoles());
		
		userRoles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getNome())));
		});
		
		this.authorities = authorities;
	}
	
	public static UserPrincipal create(Usuario usuario) {
		return new UserPrincipal(usuario);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public void clearPassword() {
		this.password = "";
	}
	

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
