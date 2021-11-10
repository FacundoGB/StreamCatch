package com.StreamCatch.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.StreamCatch.app.Service.UserService;

@Controller
@RequestMapping("/usuario")
public class UserController  {

	@Autowired
	private UserService usrService;
	
	private final String viewPath = "usuario/";
	
	@GetMapping()
	public String usrPanel(ModelMap UserModel) {
		
		
		return null;
	}
	
	
	
	@GetMapping("/crear")
	public String create() {

		return this.viewPath.concat("crear-usuario");

	}
	
	@PostMapping("/create")
	public String runCreate(ModelMap model, @RequestParam("name") String name, 
			@RequestParam("surname") String surname, @RequestParam("email") String email, 
			@RequestParam("password") String password) {

		try {

			usrService.createUser(name, surname, email, password);
			

		} catch (Exception e) {
			model.put("error", e.getMessage());
			return "register";
		}
		
		return "redirect:/".concat(viewPath);

	}
	
}
