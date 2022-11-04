package com.bolsaideas.springboot.backend.optica.models.Anotaciones;

import java.text.DateFormat;
import java.util.Date;

import com.bolsaideas.springboot.backend.optica.Anotaciones.JsonAtributo;

public class UsuarioAnot {

  @JsonAtributo(nombre = "nombre")
  private String nombre;

  @JsonAtributo(nombre = "contrasenia")
  private String contrasenia;
  @JsonAtributo(nombre = "telefono")
  private String telefono;
  
  @JsonAtributo(nombre = "fecha")
  private String fecha;



  public String getFecha() {
    return fecha;
  }

  public void setFecha(String fecha) {
    this.fecha = fecha;
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


}
