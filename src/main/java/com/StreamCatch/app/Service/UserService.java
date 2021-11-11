package com.StreamCatch.app.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.User;
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
	public Users createUser(String name, String surname, String email, String password) throws ErrorException {

		validate(name, surname, email, password);
		
		Users u = new Users();
		u.setName(name);
		u.setSurname(surname);
		u.setEmail(email);
		
        String encrypted = new BCryptPasswordEncoder().encode(password);
        u.setPassword(encrypted);
		
        try {
			repo.save(u);
			return u;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		

	}
	
	//MODIFICAR
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void modifyUsr(String id, String name, String surname, String email, String password) throws ErrorException{
		
		try {
			Users u = repo.getById(id);
			u.setName(name);
			u.setSurname(surname);
			u.setEmail(email);
	        String encrypted = new BCryptPasswordEncoder().encode(password);
	        u.setPassword(encrypted);
			
			repo.save(u);
			
		} catch (Exception e) {
			throw new ErrorException("Huno un problema en la actualizacion del Usuario");
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
	
	
	/*
	 * BUSQUEDAS --------
	 * 
	 */
	
	public Users returnUser(String id) {
		Users u = repo.getById(id);
		return u;
		
	}
	
	@Transactional(readOnly=true)
	public List<Users> listUsers(){
		return repo.findAll();
	}
	
	@Transactional(readOnly=true)
	public Users findById(String id) throws ErrorException{
		Optional<Users> answer = repo.findById(id);
		
		if(!answer.isEmpty()) {
			return answer.get();
			
		}else {
			throw new ErrorException("No existe usuario con dicho id");
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

	//PERMISOS
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users u = repo.searchByEmail(email);
		
		if (u == null) {
			return null;
		}		
				
		
		List<GrantedAuthority> permissions = new ArrayList<>();
		GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + u.getRol().toString());
		permissions.add(p1);

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

		// Se crea la sesion y se agrega el cliente a la misma
		HttpSession session = attr.getRequest().getSession(true);
		session.setAttribute("usersession", u);

		// Se retorna el usuario con sesion "iniciada" y con permisos
		return new User(email, u.getPassword(), permissions);

		
		
	}
	
	
}
