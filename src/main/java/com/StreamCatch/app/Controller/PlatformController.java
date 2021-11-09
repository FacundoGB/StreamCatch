package com.StreamCatch.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.StreamCatch.app.Entity.Platform;
import com.StreamCatch.app.Entity.Users;
import com.StreamCatch.app.Service.PlatformService;

@Controller
@RequestMapping("/platform")
public class PlatformController {
	
	@Autowired
	private PlatformService platService;
	private final String viewPath = "platform/";
	
	@GetMapping("/crear")
	public String create() {

		return this.viewPath.concat("crear-plataforma");

	}
	
	// LISTAR PLATAFORMAS //
	@GetMapping("/list")
	public String index(ModelMap userModel) {

		List<Platform> myPlatforms = platService.listPlatforms();
		userModel.addAttribute("platforms", myPlatforms);

		return "platforms";

	}
	
	@PostMapping("/create")
	public String runCreate(ModelMap model, @RequestParam("name") String name, 
			@RequestParam("price") double price, @RequestParam("file") MultipartFile file) {

		try {

			platService.createPlatform(file, name, name);
			

		} catch (Exception e) {
			model.put("error", e.getMessage());
			return "register";
		}
		
		return "redirect://".concat(viewPath);

	}

}
