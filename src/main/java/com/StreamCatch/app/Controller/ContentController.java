package com.StreamCatch.app.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.StreamCatch.app.Entity.Content;
import com.StreamCatch.app.Interfaces.ErrorHandler;
import com.StreamCatch.app.Service.ContentService;


@Controller
@RequestMapping("/content")
public class ContentController implements ErrorHandler {

	@Autowired
	private ContentService contentService;
	/*private final String viewPath = "content/";*/

	
	////////////////////////////// LISTAR CONTENIDO ///////////////////////////////////
	
	@GetMapping("/list")
	public String index(ModelMap contentModel) {

		List<Content> myContent = contentService.listContent();
		contentModel.addAttribute("content", myContent);

		return "content";

	}
	
	
		
	@GetMapping("/detalles/{id}")
	public String index(ModelMap contentModel, @PathVariable("id") String id) {

		try {
			contentModel.addAttribute("content", contentService.findById(id));
		} catch (Exception e) {
			contentModel.put("error", e.getMessage());
		}
		
		return "detalles";

	}
		
	
	
	/*/@GetMapping()
	public String contentPanel(ModelMap ContentModel) {	
		return null;
	}*/
	
	
	//////////////////////////////// CREAR CONTENIDO ///////////////////////////////////	
	
	
	// @PreAuthorize("hasAnyRole('ROLE_ADMIN')") //
	@GetMapping("/create")
	public String addContent() {

		return "createContent";

	}
	
	
	// @PreAuthorize("hasAnyRole('ROLE_ADMIN')") //
	@PostMapping("/create")
	public String runCreate(ModelMap model, @RequestParam("file") MultipartFile file, @RequestParam("name") String name) {

		try {

			contentService.createContent(file, name);
			return "redirect:/content/list";

		} catch (Exception e) {
			model.put("error", e.getMessage());
			return "createContent";
		}
		
	}
	
	
	/////////////////////////////// MODIFICAR CONTENIDO //////////////////////////////////////
	
	
	// @PreAuthorize("hasAnyRole('ROLE_ADMIN')") //
	@GetMapping("/update/{id}")
	public String update(ModelMap model, @PathVariable("id") String id) {
		try {
			model.addAttribute("content", contentService.findById(id));
			
		} catch (Exception e) {
			return this.errorHandler(e, model);
		}
			
		return "modContent";
	}
	
	
	// @PreAuthorize("hasAnyRole('ROLE_ADMIN')") //
	@PostMapping("/update/{id}")
	public String updateContent(ModelMap model, @RequestParam("file") MultipartFile file, 
			@RequestParam("name") String name, @PathVariable("id") String id) {
			
				
		try {
			
			contentService.updateContent(file, name, id);
				
		} catch (Exception e) {
			
			this.errorHandler(e, model);
		}
		
		return "redirect:/content/list";
	}

		
	//////////////////////////////////// ELIMINAR CONTENIDO /////////////////////////////////////
	
	
	// @PreAuthorize("hasAnyRole('ROLE_ADMIN')") //
	@GetMapping("/remove/{id}")
	public String remove(ModelMap model, @PathVariable("id") String id) {
			
		try {
			contentService.removeById(id);
			return "redirect:/content/list";
				
		} catch (Exception e) {
			return this.errorHandler(e, model);
		}
	}
		
	
	// BARRA BUSQUEDA //
	
	
	@GetMapping(value = "/busqueda")
	public String busquedaContent(ModelMap model, @RequestParam(value="query", required = false) String q) {
		try {
			
			List<Content> content =this.contentService.findByName(q);			
			model.addAttribute("content", content);
			model.addAttribute("resultado",q);
						
			return "busqueda";
			
		}catch (Exception e) {
			
			model.addAttribute("error", e.getMessage());
			return "error" ;
		}
	}

	
	
	
		
	@Override
	public String errorHandler(Exception e, ModelMap model) {
		model.addAttribute("err", e.getMessage());
		return this.index(model);
	}

}
