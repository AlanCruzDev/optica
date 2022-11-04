package com.bolsaideas.springboot.backend.optica.models;

import java.util.Date;

public class AccesoUser {
	
	private long id;
	private Date fecha;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
