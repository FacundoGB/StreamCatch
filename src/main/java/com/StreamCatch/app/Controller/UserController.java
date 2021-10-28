package com.StreamCatch.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.StreamCatch.app.Service.UserService;

@Controller
@RequestMapping("/usuario")
public class UserController {

	@Autowired
	private UserService usrService;
	private final String viewPath = "usuario/";
	
}
