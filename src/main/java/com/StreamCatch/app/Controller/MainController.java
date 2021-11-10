package com.StreamCatch.app.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


 

@Controller
@RequestMapping("/")
public class MainController {
	
	@GetMapping("")
	public String index() {
		
		return "index";
	}
	
	


}
