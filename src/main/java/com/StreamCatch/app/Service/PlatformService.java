package com.StreamCatch.app.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.StreamCatch.app.Entity.Platform;
import com.StreamCatch.app.Entity.Users;
import com.StreamCatch.app.Exceptions.ErrorException;
import com.StreamCatch.app.Exceptions.ServiceError;
import com.StreamCatch.app.Exceptions.ValidationError;
import com.StreamCatch.app.Repository.PlatformRepository;
import com.StreamCatch.app.Repository.UserRepository;

@Service
public class PlatformService {

	@Autowired
	private PlatformRepository repo;
	
	@Autowired
	private UserRepository usrRepo;
	
	/*
	 * ------- CRUD ---------
	 * 
	 */
	
	
	//CREAR
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void createPlatform(MultipartFile file, String name, String price)  {
		try {
			Platform platform = new Platform();

			Double priceDouble = Double.parseDouble(price);
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			validate(fileName, name, price);

			if (fileName.contains("..")) {
				throw new ValidationError("Tipo de archivo invalido");
			}
			
			try {
				
				platform.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			platform.setName(name);
			platform.setPrice(priceDouble);
			platform.setStatus(true);

			repo.save(platform);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			
		}

	}
	
	//MODIFICAR
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void updatePlatform(String id, MultipartFile file, String name, String price) throws ErrorException{
		
		Double priceDouble = Double.parseDouble(price);
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		validate(fileName, name, price);
		
		if(fileName.contains("..")) {
			throw new ValidationError("Tipo de archivo invalido");
		}

		try {	
			
			Platform platform = findPlatformById(id);
			
			try {
				platform.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			platform.setName(name);
			platform.setPrice(priceDouble);

			repo.save(platform);
			
		} catch (Exception e) {
			throw new ErrorException("Huno un problema en la actualizacion de la Plataforma");
		}
	


	}
	
	//ELIMINAR
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void deletePlatform(String id) throws ErrorException{
		
		try {
			repo.deleteById(id);			
		} catch (Exception e) {
			throw new ErrorException("Error al eliminar la plataforma");
		}
	}
	
	//CAMBIAR ALTA BAJA USUARIO
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void statusChange(String id) throws ErrorException{
		
		try {
			Platform p = repo.getById(id);
			p.setStatus(!p.getStatus());
			repo.save(p);
		} catch (Exception e) {
			throw new ErrorException("No se pudo modificar el estado de la Plataforma");
		}
	
	}

	//SUBSCRIPCION
	public void suscribe(String id_user, String id_platform) throws ErrorException{
		
		Platform platform = repo.getById(id_platform);
		Users user = usrRepo.getById(id_user);
		platform.getUsers().add(user);
		repo.save(platform);
		
	}
	
	//UN-SUBSCRIPCION
	public void unsuscribe(String id_user, String id_platform) throws ErrorException{
		
		Platform platform = repo.getById(id_platform);
		Users user = usrRepo.getById(id_user);
		
		List<Users> userList = platform.getUsers();
		userList.removeIf(use -> use  == user);
		repo.save(platform);
		
	}
	
	//EVALUAR SI EL USUARIO ESTA SUBSCRIPTO
	public boolean evaluateSubscription(String id_user, String id_platform) {
		
		Platform platform = repo.getById(id_platform);
		Users user = usrRepo.getById(id_user);
		List<Users> userList = platform.getUsers();
		
		for (Users use : userList) {
			
			if (use==user) {
				System.out.println("retorna true");
				return true;
				
			}
		}
		System.out.println("retorna false");
		return false;
	}
	
	//-------------


	
	
	/*
	 * -------- BUSQUEDAS --------
	 */
	
	
	
	
	//BUSCAR PLATAFORMA POR NOMBRE
	public Optional<Platform> searchPlatformByName(String name) throws ErrorException{
		
		Optional<Platform> platformList = repo.findByName(name);
		
		if(!platformList.isPresent()) {
			return platformList;
			
		}else {
			throw new ErrorException("No hay ninguna plataforma con este nombre");
		}
	}
	
	//BUSCAR PLATAFORMAS DE UN USUARIO
	public List<Platform> findPlatformByUser(String id_user) throws ErrorException{
		
		Users user = usrRepo.getById(id_user);
		List<Platform> platformList = repo.findByUsers(user);
		List<Platform> platformListInUser = new ArrayList<Platform>();
		
		for (Platform platform : platformList) {
			
			if (platform.getStatus()) {
				platformListInUser.add(platform);
				System.out.println(platform);
			}
		}
		
		if(!platformListInUser.isEmpty()) {
			return platformListInUser;
		}else {
			throw new ErrorException("No hay cursos plataformas para este usuario");

		}
 	}
	
	
	//LISTAR PLATAFORMAS 
	@Transactional(readOnly = true)
	public List<Platform> listPlatforms(){
		return repo.findAll();	
	}
	
	
	@Transactional(readOnly=true)
	public Platform returnPlatform(String id) {
		Platform p = repo.getById(id);
		return p;
		
	}
	
	//ENCONTRAR POR ID
	@Transactional(readOnly=true)
	public Platform findPlatformById(String id) throws ErrorException{
		Optional<Platform> answer = repo.findById(id);
		
		if(answer.isPresent()) {
			return answer.get();
			
		}else {
			throw new ErrorException("No existe Plataforma con dicho id");
		}
	}
	
	
	
	
	/*
	 * ------- VALIDATION ----------
	 */

	public void validate(String fileName, String name, String price) {
		
		if(fileName.contains("..")) {
			throw new ValidationError("Tipo de archivo invalido");
		}

		if (name == null || name.isEmpty() || name.contains("  ")) {
			throw new ValidationError("Debe tener un nombre valido");
		}

		if (price == null || price.isEmpty()) {
			throw new ValidationError("Debe tener un precio");
		}
	}
	
}
