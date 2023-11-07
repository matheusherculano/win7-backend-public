package com.gestor.app.oldPhpApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_LEADS")
public class TblLead {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_LEAD")
    private Long id;
	
	@Column(name = "TXT_LEADTIPO") 
    private String leadTipo;

	@Column(name = "NU_LIXEIRA") 
	private Long nuLixeira;

	@Column(name = "DT_ATUAL") 
	private String dtAtual;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CLIENTE_FK")
	private TblCliente tblCliente;
	
	public TblLead() {
		// TODO Auto-generated constructor stub
	}
	

	public String getDtAtual() {
		return dtAtual;
	}


	public void setDtAtual(String dtAtual) {
		this.dtAtual = dtAtual;
	}


	public Long getNuLixeira() {
		return nuLixeira;
	}


	public void setNuLixeira(Long nuLixeira) {
		this.nuLixeira = nuLixeira;
	}


	public TblCliente getTblCliente() {
		return tblCliente;
	}


	public void setTblCliente(TblCliente tblCliente) {
		this.tblCliente = tblCliente;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLeadTipo() {
		return leadTipo;
	}

	public void setLeadTipo(String leadTipo) {
		this.leadTipo = leadTipo;
	}
	
	

}
