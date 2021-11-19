package com.StreamCatch.app.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.StreamCatch.app.Exceptions.ErrorException;
import com.StreamCatch.app.Interfaces.ErrorHandler;
import com.StreamCatch.app.Repository.PlatformRepository;
import com.StreamCatch.app.Service.PlatformService;

@Controller
@RequestMapping("/platform")
public class PlatformController implements ErrorHandler{
	
	@Autowired
	private PlatformService platService;
	@Autowired
	private PlatformRepository platRepo;

	
	
	// LISTAR PLATAFORMAS //
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN' )")
	@GetMapping("/list")
	public String index(ModelMap userModel) {

		List<Platform> myPlatforms = platService.listPlatforms();
		userModel.addAttribute("platforms", myPlatforms);

		return "pruebaFacu/platform";

	}
	
	@GetMapping("/findOne")
	public Platform findOne(String id) {
		return platRepo.getById(id);
	}
	
	
	// VER UNA PLATAFORMA //
//	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@GetMapping("/viewPlatform")
	public String viewPlatform(ModelMap userModel, @RequestParam String id_platform, HttpSession session)  throws ErrorException {

		System.out.println(id_platform);

		Platform platform = platService.findPlatformById(id_platform);
		Users loggedUser = (Users) session.getAttribute("usersession");
		
		System.out.println(loggedUser);
		// platService.findPlatformById(id);
		userModel.addAttribute("platform", platform);

		Boolean value = platService.evaluateSubscription(loggedUser.getId(), id_platform);

		platService.suscribe(loggedUser.getId(), id_platform);
		userModel.addAttribute("suscrito", value);
		System.out.println(id_platform);

		System.out.println("No entraste kpo");
		return "platform/platformView.html";

	}
	
	// CREAR PLATAFORMAS //
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/create")
	public String createPlatform(){
		
		return "pruebaFacu/createPlatform";		
	}
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/create")
	public String runCreate(ModelMap model, @RequestParam("file") MultipartFile file, @RequestParam("name") String name, 
			@RequestParam("price") String price) {
			
		try {
			
			platService.createPlatform(file, name, price);
			return "redirect:/platform/list";

		} catch (Exception e) {
			model.put("error", e.getMessage());
			return "pruebaFacu/createPlatform";
		}

	}
	
	
	// SUSCRIBIR Y DESUSCRIBIR // 
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
@GetMapping("/suscribe/{id_platform}")
public String addPlatformToUser(@RequestParam("id_user") String id_user,  @RequestParam("id_platform") String id_platform) throws ErrorException{
	
	platService.suscribe(id_user, id_platform);
	return "redirect:/"; //que me retorne al index
}

@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
@PostMapping("/unsuscribe/{id_platform}")
public String removePlatformToUser(@RequestParam("id_user") String id_user,  @RequestParam("id_platform") String id_platform) throws ErrorException{
	platService.unsuscribe(id_user, id_platform);
	return "redirect:/";
}


	
	// -------------- // 
	
	// UPDATEAR PLATAFORMAS //
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/update/{id}")
	public String update(ModelMap model, @PathVariable("id") String id) {
		try {
			model.addAttribute("platform", platService.findPlatformById(id));
			
		} catch (Exception e) {
			return this.errorHandler(e, model);
		}
		
		return "pruebaFacu/modPlatform.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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
	
	// ------------- //
	
	// ELIMINAR PLATAFORMAS //
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/remove/{id}")
	public String remove(ModelMap model, @PathVariable("id") String id) {
		
		try {
			platService.deletePlatform(id);
			return "redirect:/platform/list";
			
		} catch (Exception e) {
			return this.errorHandler(e, model);
		}
	}
	
	// -------------- //
	
	// SUSCRIBIR Y DESUSCRIBIR // 
//	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
//	@PostMapping("")
//	public String addPlatformToUser(@RequestParam("id_user") String id_user, @RequestParam("id_platfrom") String id_platform) throws ErrorException{
//		
//		platService.suscribe(id_user, id_platform);
//		return "redirect://"; //que me retorne al index
//	}
//	
//	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
//	@PostMapping("")
//	public String removePlatformToUser(@RequestParam("id_user") String id_user, @RequestParam("id_platfrom") String id_platform) throws ErrorException{
//		platService.unsuscribe(id_user, id_platform);
//		return "redirect://";
//	}
	
	// -------------- //
	
	
	@Override
	public String errorHandler(Exception e, ModelMap model) {
		model.addAttribute("err", e.getMessage());
		return this.index(model);
	}
}
