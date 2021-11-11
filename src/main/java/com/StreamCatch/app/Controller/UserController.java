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


import com.StreamCatch.app.Entity.Users;
import com.StreamCatch.app.Exceptions.ErrorException;
import com.StreamCatch.app.Exceptions.ValidationError;
import com.StreamCatch.app.Interfaces.ErrorHandler;
import com.StreamCatch.app.Service.UserService;



@Controller
@RequestMapping("/usuario")
public class UserController {

	@Autowired
	private UserService usrService;
	
	
	// REGISTRAR //
	@GetMapping("/register")
	public String register() {

		return "register/REGISTROUSUARIOSC";

	}
	
	@PostMapping("/register")
	public String Create(ModelMap model, @RequestParam("name") String name, 
			@RequestParam("surname") String surname, @RequestParam("email") String email, 
			@RequestParam("password") String password) throws ValidationError {

		try {

			usrService.createUser(name, surname, email, password);
			return "redirect:/usuario/login";

		} catch (Exception e) {
			model.put("error", e.getMessage());
			return "REGISTROUSUARIOSC";
		}

	}
	
	// ------------------ //
	
	// UPDATEAR USUARIOS //
	@GetMapping("/update/{id}")
	public String update(ModelMap model, @PathVariable("id") String id) throws ValidationError  {
		try {
			model.addAttribute("users", usrService.findById(id));
			
		} catch (Exception e) {
			model.put("error", e.getMessage());
		}
		
		return "modUser.html";
	}
	
	@PostMapping("/update/{id}")
	public String updateUser(ModelMap model, @PathVariable("id") String id, @RequestParam("name") String name, 
								@RequestParam("surname") String surname, @RequestParam("email") String email, 
								@RequestParam("password") String password) throws ValidationError {
		
		try {
			usrService.modifyUsr(id, name, surname, email, password);
			
		} catch (Exception e) {
			model.put("error", e.getMessage());
		}
		
		return "redirect:/usuario/list";
	}
	
	// ELIMINAR USUARIOS //
	@GetMapping("/remove/{id}")
	public String remove(ModelMap model, @PathVariable("id") String id) throws ErrorException {

			usrService.removeById(id);
			return "redirect:/usuario/list";
	}

}
