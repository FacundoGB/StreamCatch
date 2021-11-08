package com.StreamCatch.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.StreamCatch.app.Service.ContentService;


@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	private final String viewPath = "content/";

	@GetMapping()
	public String contentPanel(ModelMap ContentModel) {
		
		
		return null;
	}
		
	
	@GetMapping("/crear")
	public String create() {

		return this.viewPath.concat("crear-contenido");

	}

	@PostMapping("/create")
	public String runCreate(ModelMap model, @RequestParam("file") MultipartFile file, @RequestParam("name") String name) {

		try {

			contentService.createContent(file, name);
			

		} catch (Exception e) {
			model.put("error", e.getMessage());
			return "register";
		}
		
		return "redirect:/".concat(viewPath);

	}


}
