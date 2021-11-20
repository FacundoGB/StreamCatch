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

import com.StreamCatch.app.Entity.Platform;
import com.StreamCatch.app.Entity.Users;
import com.StreamCatch.app.Exceptions.ErrorException;
import com.StreamCatch.app.Exceptions.ServiceError;
import com.StreamCatch.app.Exceptions.ValidationError;
import com.StreamCatch.app.Repository.PlatformRepository;
import com.StreamCatch.app.Repository.UserRepository;
import com.StreamCatch.app.rol.Rol;




@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PlatformService pservice;


	
	/*
	 * CRUD ---------
	 */
	
	// CREAR
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void createUser(String name, String surname, String email, String password, String idPlatform) throws ServiceError{

		validate(name, surname, email, password, idPlatform);
		Platform p = pservice.findById(idPlatform);
		
		Users u = new Users();
		u.setName(name);
		u.setSurname(surname);
		u.setEmail(email);
		u.setPlatforms(p);
		u.setRol(Rol.USER);
		
        String encrypted = new BCryptPasswordEncoder().encode(password);
        u.setPassword(encrypted);
		
        try {
			repo.save(u);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
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
	public void validate(String name, String surname, String email, String password, String idPlatform) throws ServiceError {

		Optional<Users> u = repo.validationEmail(email);
		if (u.isPresent()) {
			throw new ServiceError("Este email ya esta asignado a un Usuario");
		}
		
		if (name == null || name.isEmpty() || name.contains("  ")) {
			throw new ServiceError("Debe tener un nombre valido");
		}

		if (surname == null || surname.isEmpty() || surname.contains("  ")) {
			throw new ServiceError("Debe tener un apellido valido");
		}

		if (email == null || email.isEmpty() || email.contains("  ")) {
			throw new ServiceError("Debe tener un email valido");
		}

		if (password == null || password.isEmpty() || password.length() < 8) {
			throw new ServiceError("Debe tener una clave valida");
		}
		if (idPlatform.isBlank() || idPlatform.isEmpty() || idPlatform == null) {
			throw new ServiceError("Error: ID plataforma invalido!");
		}
	}

	//PERMISOS
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users u = repo.searchByEmail(email);

		if (u != null) {
			List<GrantedAuthority> permissions = new ArrayList<>();
			GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + u.getRol().toString());
			permissions.add(p1);

			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

			// Se crea la sesion y se agrega el cliente a la misma
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("user", u);

			// Se retorna el usuario con sesion "iniciada" y con permisos
			return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(), permissions);
		}

		return null;
		
	}
	
	
}