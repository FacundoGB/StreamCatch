package com.StreamCatch.app.Service;

import java.io.IOException;
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

import com.StreamCatch.app.Exceptions.ErrorException;
import com.StreamCatch.app.Exceptions.ServiceError;
import com.StreamCatch.app.Exceptions.ValidationError;
import com.StreamCatch.app.Repository.PlatformRepository;

@Service
public class PlatformService {

	@Autowired
	private PlatformRepository repo;
	
	/*
	 * CRUD ---------
	 * 
	 */
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void createPlatform(MultipartFile file, String name, String price)  {
		
		Platform platform = new Platform();
		
		Double priceDouble = Double.parseDouble(price);
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		//validate(fileName, name, price);
		
		if(fileName.contains("..")) {
			throw new ValidationError("Tipo de archivo invalido");
		}
		try {
			platform.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		platform.setName(name);
		platform.setPrice(priceDouble);
		
		repo.save(platform);
	};
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void updatePlatform(String id, MultipartFile file, String name, String price) throws ErrorException{
		
		Double priceDouble = Double.parseDouble(price);
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		//validate(fileName, name, price);
		
		if(fileName.contains("..")) {
			throw new ValidationError("Tipo de archivo invalido");
		}

		try {	
			
			Platform platform = repo.getById(id);
			
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
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void deletePlatform(String id) throws ErrorException{
		
		try {
			repo.deleteById(id);			
		} catch (Exception e) {
			throw new ErrorException("Error al eliminar la plataforma");
		}
	}
	
	@Transactional(readOnly = true)
	public List<Platform> listPlatforms(){
		return repo.findAll();	
	}
	
	@Transactional(readOnly=true)
	public Platform returnPlatform(String id) {
		Platform p = repo.getById(id);
		return p;
		
	}
	
	
	@Transactional(readOnly=true)
	public Platform findById(String id) throws ServiceError{
		Optional<Platform> answer = repo.findById(id);
		
		if(!answer.isEmpty()) {
			return answer.get();
			
		}else {
			throw new ServiceError("No existe Plataforma con dicho id");
		}
	}
	
	/*
	 * VALIDATION ----------
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
