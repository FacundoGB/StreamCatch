package com.StreamCatch.app.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.StreamCatch.app.Repository.ContentRepository;

public class ContentService {
	
	@Autowired
	private ContentRepository repo;

	/*
	 * CRUD ---------
	 */
	
	// Creacion contenido (ver el seteo de plataformas) //
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void createContent(String name, String fileName) throws ErrorException {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		validate(fileName, name);
			
		Content content = new Content();
		try {
			content.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		content.setName(name);		
		
		repo.save(content);

	}

	//MODIFICAR//
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void modifyCont(String name) throws ErrorException{
		
		validate(name);
		
		Optional<Content> answer = repo.findById(id);
		
		if(answer.isPresent()) {
			
			Content content = new Content();
			try {
				content.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			content.setName(name);
						
			repo.save(content);
			
		} else {
			throw new ErrorException("No se encontró la plataforma solicitada");
		}
		
		
	}

	//ELIMINAR//
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void removeById(String id) throws ErrorException {
			
			try {
				repo.deleteById(id);			
			} catch (Exception e) {
				throw new ErrorException("Error al eliminar el contenido");
			}
			
	}
	
	// Validaciones //
	public void validate(String fileName, String name) {
		
		if(fileName.contains("..")) {
			throw new ValidationError("Tipo de archivo invalido");
		}

		if (name == null || name.isEmpty() || name.contains("  ")) {
			throw new ValidationError("Debe tener un nombre valido");
		}
		
	}

	
}
