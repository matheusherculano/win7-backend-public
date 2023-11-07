package com.gestor.app.model;

import java.time.LocalDateTime;
import java.util.Set;

import com.gestor.app.DTO.UsuarioDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario", nullable = false)
	private Long id;
	
	@Column(name = "nome", nullable = false, length = 45)
	private String nome;

	@Column(name = "login", unique = true, nullable = false, length = 30)
	private String login;

	@Column(name = "senha", nullable = false)
	private String senha;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "whatsapp", nullable = false, length = 15)
	private String whatsapp;

	@Column(name = "timestamp")
	private LocalDateTime timeStamp; //gravar o ultimo login do usuario
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ambiente")
	private Ambiente ambiente;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_usuario_role", joinColumns = { @JoinColumn(name = "usuario_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private Set<Role> roles;

	public Usuario() {
		// TODO Auto-generated constructor stub
	}


	public Usuario(Long id, String nome, String login, String senha, LocalDateTime timeStamp, Ambiente ambiente,
			Set<Role> roles) {
		super();
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.timeStamp = timeStamp;
		this.ambiente = ambiente;
		this.roles = roles;
	}

	

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getWhatsapp() {
		return whatsapp;
	}


	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}


	public Usuario(UsuarioDTO dto) {
		this.id = dto.id();
		this.nome = dto.nome();
		this.login = dto.login().toLowerCase();
		this.senha = dto.senha();
		this.email = dto.email();
		this.whatsapp = dto.whatsapp();
		this.timeStamp = dto.timeStamp();
		this.roles = dto.roles();
		this.ambiente = new Ambiente(dto.ambiente()); //dentro do construtor coloco lowecase no nome do ambiente para manter padrão do db
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}


	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
