package com.StreamCatch.app.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.StreamCatch.app.Repository.ContentRepository;

public class ContentService {
	
	@Autowired
	private ContentRepository repo;

	/*
	 * CRUD ---------
	 */
	
	// CREACIÓN CONTENIDO //
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void createContent(String name, MultipartFile file) throws ErrorException {
		
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

	// MODIFICAR CONTENIDO //
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void updateContent(String name, MultipartFile file) throws ErrorException{
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		validate(fileName, name);
		
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
			throw new ErrorException("No se encontró el contenido solicitado");
		}
		
		
	}

	// BORRAR CONTENIDO //
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { ErrorException.class, Exception.class })
	public void deleteContent(String id) throws ErrorException {
			
			try {
				repo.deleteById(id);			
			} catch (Exception e) {
				throw new ErrorException("Error al eliminar el contenido");
			}
			
	}


	@Transactional(readOnly = true)
	public List<Content> findAll(){
		return repo.findAll();	
	}
	
	// VALIDACIÓN DE CONTENIDO //
	public void validate(String fileName, String name) {
		
		if(fileName.contains("..")) {
			throw new ValidationError("Tipo de archivo invalido");
		}

		if (name == null || name.isEmpty() || name.contains("  ")) {
			throw new ValidationError("Debe tener un nombre valido");
		}
		
	}

	
}
