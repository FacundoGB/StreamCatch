package com.StreamCatch.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.StreamCatch.app.Entity.Platform;
import com.StreamCatch.app.Entity.Users;
import com.StreamCatch.app.Interfaces.ErrorHandler;
import com.StreamCatch.app.Service.PlatformService;

@Controller
@RequestMapping("/platform")
public class PlatformController implements ErrorHandler{
	
	@Autowired
	private PlatformService platService;
	private final String viewPath = "platform/";
	
	
	// LISTAR PLATAFORMAS //
	@GetMapping("/list")
	public String index(ModelMap userModel) {

		List<Platform> myPlatforms = platService.listPlatforms();
		userModel.addAttribute("platforms", myPlatforms);

		return "platform";

	}
	
	// CREAR PLATAFORMAS //
	@GetMapping("/create")
	public String createPlatform(){
		
		return "createPlatform";		
	}
	
	@PostMapping("/create")
	public String runCreate(ModelMap model, @RequestParam("file") MultipartFile file, @RequestParam("name") String name, 
			@RequestParam("price") String price) {
			
		try {
			
			platService.createPlatform(file, name, price);
			return "redirect:/platform/list";

		} catch (Exception e) {
			model.put("error", e.getMessage());
			return "createPlatform";
		}

	}
	
	// UPDATEAR PLATAFORMAS //
	@GetMapping("/update/{id}")
	public String update(ModelMap model, @PathVariable("id") String id) {
		try {
			model.addAttribute("platform", platService.findById(id));
			
		} catch (Exception e) {
			return this.errorHandler(e, model);
		}
		
		return "modPlatform.html";
	}
	
	@PostMapping("/update/{id}")
	public String updateUser(ModelMap model, @PathVariable("id") String id, @RequestParam("file") MultipartFile file, @RequestParam("name") String name, 
							@RequestParam("price") String price) {
		
		try {
			
			platService.updatePlatform(id, file, name, price);
			
			
		} catch (Exception e) {
			this.errorHandler(e, model);
		}
		
		return "redirect:/platform/list";
	}
	
	// ELIMINAR PLATAFORMAS //
	@GetMapping("/remove/{id}")
	public String remove(ModelMap model, @PathVariable("id") String id) {
		
		try {
			platService.deletePlatform(id);
			return "redirect:/platform/list";
			
		} catch (Exception e) {
			return this.errorHandler(e, model);
		}
	}
	
	
	
	@Override
	public String errorHandler(Exception e, ModelMap model) {
		model.addAttribute("err", e.getMessage());
		return this.index(model);
	}
}
