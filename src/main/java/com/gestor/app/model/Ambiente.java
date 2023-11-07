package com.gestor.app.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_ambiente")
public class Ambiente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ambiente", nullable = false)
	private Long id;
	
	@Column(name = "nome_acesso", nullable = false)
	private String nomeAcesso;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@JsonIgnore
	@OneToMany(mappedBy = "ambiente")
	private List<Usuario> usuarios;
	
	public Ambiente() {
	}
	
	

	public Ambiente(Ambiente a) {
		super();
		
		if(a != null) {
			this.id = a.getId();
			this.nomeAcesso = a.getNomeAcesso().toLowerCase(); //mantendo padrão de login e ambiente lowercase no db
			this.nome = a.nome;
			this.usuarios = a.getUsuarios();
		}
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeAcesso() {
		return nomeAcesso;
	}

	public void setNomeAcesso(String nomeAcesso) {
		this.nomeAcesso = nomeAcesso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	
	
}
