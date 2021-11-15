package com.StreamCatch.app.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.StreamCatch.app.Entity.Platform;
import com.StreamCatch.app.Service.PlatformService;




@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private PlatformService platService;

	@GetMapping("")
	public String index(ModelMap userModel) {
		
		List<Platform> myPlatforms = platService.listPlatforms();
		userModel.addAttribute("platforms", myPlatforms);
		return "index";
	}

	@GetMapping("/login")
	public String login(HttpSession session, Authentication user, @RequestParam(required = false) String error, ModelMap modelo) {

		try {
			
			if (user.getName() != null) {
				return "redirect:/";
				
			} else {
				
				if (error != null && !error.isEmpty()) {
					modelo.put("error", "Nombre de usuario o clave incorrecta");
					
				}
				return "user/login";
			}
			
		} catch (Exception e) {
			if (error != null && !error.isEmpty()) {
				modelo.put("error", "La dirección de mail o la contraseña que ingresó son incorrectas.");
			}

			return "user/login";
		}
	}
	
	@GetMapping("/loginsuccess")
	public String loginresolver() {
				
		return "redirect:/";
	}


}
