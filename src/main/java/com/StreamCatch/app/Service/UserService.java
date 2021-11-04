package com.StreamCatch.app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.StreamCatch.app.Entity.User;
import com.StreamCatch.app.Exceptions.ErrorException;
import com.StreamCatch.app.Exceptions.ValidationError;
import com.StreamCatch.app.Repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	@Autowired
	private ContentService ContentService;

	
	/*
	 * CRUD ---------
	 */
	
	// Creacion usuario ver el seteo de plataformas //
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public User createUser(String name, String surname, String email, String password) throws ErrorException {
		
		validate(name, surname, email, password);
		
		User user = new User();
		user.setName(name);
		user.setSurname(surname);
		user.setEmail(email);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		
		return repo.save(user);

	}
	
	//MODIFICAR
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public User modifyUsr(String name, String surname, String email, String password) throws ErrorException{
		
		validate(name, surname, email, password);
		
		User user = new User();
		user.setName(name);
		user.setSurname(surname);
		user.setEmail(email);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		
		return repo.save(user);
		
	}
	
	//ELIMINAR
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void removeById(String id) throws ErrorException {
			
			try {
				repo.deleteById(id);			
			} catch (Exception e) {
				throw new ErrorException("Error al eliminar el usuario");
			}
			
		}
	/*
	 * VALIDATION ---------
	 */
	// Agregar UserRepository.searchByEmail/searchByMovie/searchBySerie/searchByPlatform //
	public void validate(String name, String surname, String email, String password) {
		if (name == null || name.isEmpty() || name.contains("  ")) {
			throw new ValidationError("Debe tener un nombre valido");
		}

		if (surname == null || surname.isEmpty() || surname.contains("  ")) {
			throw new ValidationError("Debe tener un apellido valido");
		}

		if (email == null || email.isEmpty() || email.contains("  ")) {
			throw new ValidationError("Debe tener un email valido");
		}

		if (password == null || password.isEmpty() || password.length() < 8) {
			throw new ValidationError("Debe tener una clave valida");
		}
	}

}
