package com.bolsaideas.springboot.backend.optica.controllers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bolsaideas.springboot.backend.optica.models.AccesoUser;
import com.bolsaideas.springboot.backend.optica.models.Usuario;
import com.bolsaideas.springboot.backend.optica.repository.UserRepository;
import ch.qos.logback.classic.Logger;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("v1/api/user")
public class UserController {

	@Autowired
	private UserRepository repositoryUserRepository;
	private static final Logger log = (Logger) org.slf4j.LoggerFactory.getLogger(UserController.class);

	@GetMapping("/all")
	public ResponseEntity<?> GetAllUsers(@RequestParam(required = false) String title) {
		List<Usuario> users = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();
		try {
			if (title == null) {
				repositoryUserRepository.findAll().forEach(users::add);
			}
			if (users.isEmpty()) {
				response.put("Error", "NO CONTENT");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(users, HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("Error", e.getMessage());
			response.put("Error Specific", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/login/{user}/{pass}")
	public ResponseEntity<?> LogUser(@PathVariable("user") String user, @PathVariable("pass") String pass) {
		Map<String, Object> response = new HashMap<>();
		List<AccesoUser> lista = new ArrayList<>();
		try {
			Optional<AccesoUser> res = repositoryUserRepository.UserAccess(user, pass);
			res.stream().forEach(lista::add);
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (DataAccessException ex) {
			response.put("Error", ex.getMessage());
			response.put("Error Specific", ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/one/{id}")
	public ResponseEntity<?> GetUserId(@PathVariable("id") Long id){

		Map<String,Object> response = new HashMap<>();
		try{
				Usuario user = repositoryUserRepository.findById(id);
				response.put("data", user);
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		}catch(DataAccessException ex){
			response.put("Error", ex.getMessage());
			response.put("Error Specific", ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/insert")
	public ResponseEntity<?> PostUsuario(@RequestBody Usuario usuario) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<?> data = repositoryUserRepository.SaveUsuario(usuario);
			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (DataAccessException ex) {
			response.put("Error", ex.getMessage());
			response.put("Error Specific", ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}