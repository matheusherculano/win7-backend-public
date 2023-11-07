package com.gestor.app.phone3xc.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cl_calls")
public class Cl_calls {

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "start_time")
	private LocalDateTime start_time;

	public Cl_calls() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getStart_time() {
		return start_time;
	}

	public void setStart_time(LocalDateTime start_time) {
		this.start_time = start_time;
	}
	
	
}
