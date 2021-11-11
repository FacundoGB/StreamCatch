package com.StreamCatch.app.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String login(@RequestParam(required = false) String error, ModelMap modelo) {

		if (error != null) {
			modelo.put("error", "Nombre de usuario o clave incorrecta");
		}

		return "user/login";
	}
	


}
