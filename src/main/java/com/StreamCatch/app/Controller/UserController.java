package com.StreamCatch.app.Controller;

import java.util.ArrayList;
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

import com.StreamCatch.app.Entity.Platform;
import com.StreamCatch.app.Entity.Users;
import com.StreamCatch.app.Exceptions.ErrorException;
import com.StreamCatch.app.Exceptions.ValidationError;
import com.StreamCatch.app.Interfaces.ErrorHandler;
import com.StreamCatch.app.Repository.PlatformRepository;
import com.StreamCatch.app.Service.PlatformService;
import com.StreamCatch.app.Service.UserService;




@Controller
@RequestMapping("/usuario")
public class UserController {

	@Autowired
	private UserService usrService;
	@Autowired
	private PlatformService platService;
	@Autowired 
	PlatformRepository repo;
	
	
	// PERFIL DEL USUARIO //
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@GetMapping("/{id}")
	public String userData(HttpSession session, ModelMap userModel, @PathVariable("id") String id_user ) {
		
		Users logedUser = (Users) session.getAttribute("usersession");
		
		if (logedUser == null || !logedUser.getId().equals(id_user)) {
			return "redirect:/index.html";
		}
		
		try {
			
			List<Platform> myPlatforms = platService.listPlatforms();
			userModel.addAttribute("platforms", myPlatforms);
			Users user = usrService.findById(id_user);
			userModel.addAttribute("user", user);
			
		} catch (Exception e) {
			
			userModel.put("Error", e.getMessage());
			System.out.println("Error al ingreso del usuario a su perfil");
		}
	
		return "user/profile.html";
	}
	
	
	// ------------------ //
	
	// REGISTRAR USUARIO //
	@GetMapping("/registro")
	public String register(ModelMap model) {
//		List<Platform> myPlatforms = platService.listPlatforms();
//		model.addAttribute("platforms", myPlatforms);

		return "user/REGISTROUSUARIOSC.html";

	}
	
	@PostMapping("/registro")
	public String Create(ModelMap model, @RequestParam("name") String name, 
			@RequestParam("surname") String surname, @RequestParam("email") String email, 
			@RequestParam("password") String password /*@RequestParam("idPlatform")ArrayList<String> idPlatforms*/) throws Exception {

		try {

//			for (String a : idPlatforms) {
//				System.out.println(a);
//				
//			}
			usrService.createUser(name, surname, email, password);
			System.out.println("EXITO");
			return "redirect:/login";

		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("error", e.getLocalizedMessage());
			return "redirect:/index.html";
		}

	}
	
	// ------------------ //
	
	// UPDATEAR USUARIOS //
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@GetMapping("/update/{id}")
	public String update(HttpSession session, ModelMap model, @PathVariable("id") String id) throws ValidationError  {
		
		Users logedUser = (Users) session.getAttribute("usersession");
		
		if (logedUser == null || !logedUser.getId().equals(id)) {
			return "redirect:/index.html";
		}
		
		try {
			
			model.addAttribute("users", usrService.findById(id));
			return "modUser.html";
			
		} catch (Exception e) {
			
			model.put("error", e.getMessage());
			System.out.println("Error en la modificacion del perfil");
		}
		
		return "modUser.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN' )")
	@PostMapping("/update/{id}")
	public String updateUser(HttpSession session, ModelMap model, @PathVariable("id") String id, @RequestParam("name") String name, 
								@RequestParam("surname") String surname, @RequestParam("email") String email, 
								@RequestParam("password") String password) throws ValidationError {
		
		Users logedUser = (Users) session.getAttribute("usersession");
		
		try {
			
			usrService.modifyUsr(id, name, surname, email, password);
			session.setAttribute("usersession", logedUser);
			
		} catch (Exception e) {
			model.put("error", e.getMessage());
			System.out.println("Error en el metodo Post para modificacion datos usuario: " + e.getMessage());
		}
		
		return "redirect:/usuario/list";
	}
	
	// ------------------ //
	
	
	// ELIMINAR USUARIOS //
	@GetMapping("/remove/{id}")
	public String remove(ModelMap model, @PathVariable("id") String id) throws ErrorException {

			usrService.removeById(id);
			return "redirect:/usuario/list";
	}
	// ------------------ //

}
