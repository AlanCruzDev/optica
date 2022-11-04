package com.bolsaideas.springboot.backend.optica.models.Mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.bolsaideas.springboot.backend.optica.models.Usuario;


public class UsuarioMapper implements RowMapper<Usuario> {

  @Override
  public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {

    Usuario user = new Usuario();
    user.setId(rs.getLong("id"));
    user.setNombre(rs.getString("nombre"));
    
    user.setTelefono(rs.getString("telefono"));
    user.setFecha(rs.getDate("fecha"));
    return user;
  }
  
}
