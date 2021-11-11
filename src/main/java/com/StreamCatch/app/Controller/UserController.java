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
import com.StreamCatch.app.Interfaces.ErrorHandler;
import com.StreamCatch.app.Service.UserService;



@Controller
@RequestMapping("/usuario")
public class UserController implements ErrorHandler {

	@Autowired
	private UserService usrService;
	private final String viewPath = "usuario/";
	
	
	// LISTAR USUARIOS //
	@GetMapping("/list")
	public String index(ModelMap userModel) {

		List<Users> myUsers = usrService.listUsers();
		userModel.addAttribute("users", myUsers);

		return "user";

	}
	
	// CREAR USUARIOS //
	@GetMapping("/create")
	public String addAuthors(){
		
		return "registerUser";		
	}
	
	@PostMapping("/create")
	public String Create(ModelMap model, @RequestParam("name") String name, 
			@RequestParam("surname") String surname, @RequestParam("email") String email, 
			@RequestParam("password") String password) {

		try {

			usrService.createUser(name, surname, email, password);
			return "redirect:/usuario/list";

		} catch (Exception e) {
			model.put("error", e.getMessage());
			return "registerUser";
		}

	}
	
	// UPDATEAR USUARIOS //
	@GetMapping("/update/{id}")
	public String update(ModelMap model, @PathVariable("id") String id) {
		try {
			model.addAttribute("users", usrService.findById(id));
			
		} catch (Exception e) {
			return this.errorHandler(e, model);
		}
		
		return "modUser.html";
	}
	
	@PostMapping("/update/{id}")
	public String updateUser(ModelMap model, @PathVariable("id") String id, @RequestParam("name") String name, 
								@RequestParam("surname") String surname, @RequestParam("email") String email, 
								@RequestParam("password") String password) {
		
		try {
			usrService.modifyUsr(id, name, surname, email, password);
			
		} catch (Exception e) {
			this.errorHandler(e, model);
		}
		
		return "redirect:/usuario/list";
	}
	
	// ELIMINAR USUARIOS //
	@GetMapping("/remove/{id}")
	public String remove(ModelMap model, @PathVariable("id") String id) {
		
		try {
			usrService.removeById(id);
			return "redirect:/usuario/list";
			
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
