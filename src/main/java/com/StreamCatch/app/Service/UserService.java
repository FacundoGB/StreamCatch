package com.StreamCatch.app.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.StreamCatch.app.Entity.Users;
import com.StreamCatch.app.Exceptions.ErrorException;
import com.StreamCatch.app.Exceptions.ValidationError;
import com.StreamCatch.app.Repository.UserRepository;



@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository repo;


	
	/*
	 * CRUD ---------
	 */
	
	// CREAR
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void createUser(String name, String surname, String email, String password) throws ErrorException {

		validate(name, surname, email, password);
		
		Users user = new Users();
		user.setName(name);
		user.setSurname(surname);
		user.setEmail(email);
        String encrypted = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encrypted);
		
		repo.save(user);

	}
	
	//MODIFICAR
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void modifyUsr(String id, String name, String surname, String email, String password) throws ErrorException{
		
		validate(name, surname, email, password);
		
		Optional<Users> answer = repo.findById(id);
		if(answer.isPresent()) {
			Users user = new Users();

			user.setName(name);
			user.setSurname(surname);
			user.setEmail(email);
	        String encrypted = new BCryptPasswordEncoder().encode(password);
	        user.setPassword(encrypted);
			
			repo.save(user);
			
		} else {
			throw new ErrorException("No se encontró el usuario solicitado");
		}
	
	}
	
	//ELIMINAR
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void removeById(String id) throws ErrorException {
			
			try {
				repo.deleteById(id);			
			} catch (Exception e) {
				throw new ErrorException("Error al eliminar el usr");
			}
			
		}
	
	@Transactional(readOnly=true)
	
	
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

	//PERMISOS
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users user = repo.searchByEmail(email);
				
				if (user != null) {
					List<GrantedAuthority> permissions = new ArrayList<>();
					GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRol().toString());
					permissions.add(p);
					ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
					HttpSession session = attr.getRequest().getSession(true);
					session.setAttribute("usuario", user);
					return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
							permissions);
				}
				return null;
	}
	
	
}
