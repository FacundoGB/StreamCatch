package com.StreamCatch.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String runCreate(ModelMap model, @RequestParam("name") String name) {

		try {

			contentService.createContent(name);
			

		} catch (Exception e) {
			model.put("error", e.getMessage());
			return "register";
		}
		
		return "redirect:/".concat(viewPath);

	}


}
