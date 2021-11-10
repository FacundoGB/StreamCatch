package com.StreamCatch.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.StreamCatch.app.Entity.Platform;
import com.StreamCatch.app.Entity.User;
import com.StreamCatch.app.Exceptions.ErrorException;
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
	public Platform createPlatform(String name, String price) throws ErrorException {
		
		validate(name, price);
		
		Double priceDouble = Double.parseDouble(price);
		
		Platform platform = new Platform();
		platform.setName(name);
		platform.setPrice(priceDouble);
		
		return repo.save(platform);
	};
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public Platform updatePlatform(String name, String price) throws ErrorException{
		
		validate(name, price);
		
		Double priceDouble = Double.parseDouble(price);
		
		Platform platform = new Platform();
		platform.setName(name);
		platform.setPrice(priceDouble);
		
		return repo.save(platform);
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
	public List<Platform> findAll(){
		return repo.findAll();	
	}
	
	/*
	 * VALIDATION ----------
	 */

	public void validate(String name, String price) {
		
		if (name == null || name.isEmpty() || name.contains("  ")) {
			throw new ValidationError("Debe tener un nombre valido");
		}

		if (price == null || price.isEmpty()) {
			throw new ValidationError("Debe tener un precio");
		}
	}
	
}
