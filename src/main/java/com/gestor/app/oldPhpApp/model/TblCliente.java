package com.gestor.app.oldPhpApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_CLIENTE")
public class TblCliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CLIENTE")
	private Long id;
	
	@Column(name = "TXT_NOME")
	private String nome;
	
	@Column(name = "TXT_NOME_EMPRESA")
	private String nomeEmpresa;

	@Column(name = "TXT_LOGIN")
	private String login;
	
	@Column(name = "NU_ACESSOS_SISTEMA_DATA")
	private String ultimoAcesso;
	
	@Column(name = "NU_ADWORDS")
	private String adwords;
	
	@Column(name = "TXT_CONSULTORIA")
	private String consultoria;
	
	@Column(name = "NU_WHATSAPP")
	private String whatsappAtivo;
	
	@Column(name = "NU_TELEFONE_1")
	private String telefone;

	@Column(name = "NU_VONOID")
	private String ramal;
	
	public TblCliente() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRamal() {
		return ramal;
	}

	public void setRamal(String ramal) {
		this.ramal = ramal;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(String ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	public String getAdwords() {
		return adwords;
	}

	public void setAdwords(String adwords) {
		this.adwords = adwords;
	}

	public String getConsultoria() {
		return consultoria;
	}

	public void setConsultoria(String consultoria) {
		this.consultoria = consultoria;
	}

	public String getWhatsappAtivo() {
		return whatsappAtivo;
	}

	public void setWhatsappAtivo(String whatsappAtivo) {
		this.whatsappAtivo = whatsappAtivo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	
}
