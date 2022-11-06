package com.bolsaideas.springboot.backend.optica.repository;
import java.util.List;
import java.util.Optional;
import com.bolsaideas.springboot.backend.optica.models.AccesoUser;
import com.bolsaideas.springboot.backend.optica.models.Usuario;
import com.bolsaideas.springboot.backend.optica.models.Erros.ResponseError;

public interface UserRepository {
	public Usuario findById(Long id);
	public List<Usuario> findAll();
	public Optional<AccesoUser> UserAccess(String user, String password);
	public List<ResponseError> SaveUsuario(Usuario usuario);
	public boolean DeleteUser(Long id);
}
