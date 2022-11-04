package com.bolsaideas.springboot.backend.optica.repositoryImpl;
import java.sql.Types;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.bolsaideas.springboot.backend.optica.controllers.UserController;
import com.bolsaideas.springboot.backend.optica.models.AccesoUser;
import com.bolsaideas.springboot.backend.optica.models.Usuario;
import com.bolsaideas.springboot.backend.optica.models.Anotaciones.UsuarioAnot;
import com.bolsaideas.springboot.backend.optica.models.Erros.ResponseError;
import com.bolsaideas.springboot.backend.optica.models.Mapper.UsuarioMapper;
import com.bolsaideas.springboot.backend.optica.procesador.JsonMapper;
import com.bolsaideas.springboot.backend.optica.procesador.JsonSerializador;
import com.bolsaideas.springboot.backend.optica.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;

import ch.qos.logback.classic.Logger;

@Repository
public class UsuarioRepositoryImpl implements UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;
	private SimpleJdbcCall _saveusercall;
	private static final Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(UserController.class);

	@PostConstruct
	void init() {
		simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("opticaparis")
				.withFunctionName("IniciarSesion")
				.withoutProcedureColumnMetaDataAccess();

		_saveusercall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("opticaparis")
				.withFunctionName("saveupdateuser")
				.withoutProcedureColumnMetaDataAccess()
				.declareParameters(
						new SqlParameter("_uid", Types.INTEGER),
						new SqlParameter("_data", Types.VARCHAR));
	}

	@Override
	public Usuario findById(Long id) {
		Usuario user = null;
		try{
			user = jdbcTemplate.queryForObject("select U.id, U.nombre ,U.telefono ,U.fecha  from opticaparis.usuario as U where U.id = ?", new UsuarioMapper(),new Object[]{id});
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return jdbcTemplate.query("SELECT A.* FROM opticaparis.usuario as A",
				BeanPropertyRowMapper.newInstance(Usuario.class));
	}

	@Override
	public Optional<AccesoUser> UserAccess(String user, String password) {
		Optional<AccesoUser> result = Optional.empty();
		try {
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("username", user)
					.addValue("passuser", password);
			Map<String, Object> data = simpleJdbcCall.execute(in);
			if (data != null) {
				AccesoUser accesoUser = new AccesoUser();
				accesoUser.setId(Long.parseLong("" + data.get("id")));
				accesoUser.setFecha((Date) data.get("fecha"));
				result = Optional.of(accesoUser);
			}
			return result;
		} catch (Exception e) {
			return result;
		}
	}

	@Override
	public List<ResponseError> SaveUsuario(Usuario usuario) {

		List<ResponseError> lista = null;
		if (Objects.isNull(usuario)) {
			throw new RuntimeException("El Object  no puede ser null");
		}
		UsuarioAnot useranot = new UsuarioAnot();
		useranot.setNombre(usuario.getNombre());

		if (usuario.getContrasenia().isEmpty() || usuario.getNombre().isEmpty()) {
			throw new RuntimeException("El Object  no puede ser null");
		}
		useranot.setContrasenia(usuario.getContrasenia());
		useranot.setTelefono(usuario.getTelefono());
		DateFormat formateadorFechaCorta = DateFormat.getDateInstance(DateFormat.SHORT);
		useranot.setFecha(formateadorFechaCorta.format(usuario.getFecha()));
		try {
			int _uid = 1;
			String _data = JsonSerializador.ConvertJson(useranot);
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("_uid", _uid)
					.addValue("_data", _data);
			Map<String, Object> response = _saveusercall.execute(in);
			lista = (List<ResponseError>) JsonMapper.ConvertJsonObject(response, new TypeReference<List<ResponseError>>() {});
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return lista;
	}
}
