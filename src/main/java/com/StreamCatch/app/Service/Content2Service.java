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

import com.StreamCatch.app.Entity.Content;
import com.StreamCatch.app.Entity.Content2;
import com.StreamCatch.app.Exceptions.ErrorException;
import com.StreamCatch.app.Exceptions.ValidationError;
import com.StreamCatch.app.Repository.Content2Repository;

@Service
public class Content2Service {
 @Autowired
 private Content2Repository repository;
 
 /*
  * TODO: Get the List of Shops
  */
 public List<Content2> getAllShops(){
  List<Content2> list =  (List<Content2>)repository.findAll();
  return list;
 }
 
 /*
  * TODO: Get Shop By keyword
  */
 public List<Content2> getByKeyword(String keyword){
  return repository.findByKeyword(keyword);
 }
//VALIDACIÓN DE CONTENIDO //
	
	public void validate(String fileName, String name) {
		
		if(fileName.contains("..")) {
			throw new ValidationError("Tipo de archivo invalido");
		}

		if (name == null || name.isEmpty() || name.contains("  ")) {
			throw new ValidationError("Debe tener un nombre valido");
		}
		
		
	}
	// CREACIÓN CONTENIDO //
	
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
		public void createContent(MultipartFile file, String name) throws ErrorException {
			
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			validate(fileName, name);
				
			Content2 content2 = new Content2();
			try {
				content2.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			content2.setName(name);		
			
			repository.save(content2);

		}

		// MODIFICAR CONTENIDO //
		
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
		public void updateContent(MultipartFile file, String name, String id) throws ErrorException{
			
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			validate(fileName, name);
					
			try {
				
			Content2 content2 = repository.getById(id);
			
				try {
					content2.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			
				content2.setName(name);	
				repository.save(content2);
				
			} catch (Exception e) {
				throw new ErrorException("Hubo un error de actualización de contenido");
			}
					
		}

		// BORRAR CONTENIDO //
		
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
		public void removeById(String id) throws ErrorException {
				
				try {
					repository.deleteById(id);			
				} catch (Exception e) {
					throw new ErrorException("Error al eliminar el contenido");
				}
				
		}

		//find by id
		@Transactional(readOnly=true)
		public Content2 findById(String id) throws ErrorException{
			Optional<Content2> answer = repository.findById(id);
			
			if(!answer.isEmpty()) {
				return answer.get();
				
			}else {
				throw new ErrorException("No existe contenido con dicho id");
			}
		}
		
		
		
		
}