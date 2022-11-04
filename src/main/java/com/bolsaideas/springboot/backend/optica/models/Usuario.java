package com.bolsaideas.springboot.backend.optica.models;

import java.util.Date;

public class Usuario {
	
	private Long id;
	private String nombre;
	private String contrasenia;
	private String telefono;
	private Date fecha;
	public Usuario() {
		
	}
	public Usuario(Long id, String nombre, String contrasenia, String telefono, Date fecha) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.telefono = telefono;
		this.fecha = fecha;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
