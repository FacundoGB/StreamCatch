package com.StreamCatch.app.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.StreamCatch.app.Entity.Platform;
import com.StreamCatch.app.Entity.Users;
import com.StreamCatch.app.Repository.PlatformRepository;
import com.StreamCatch.app.Service.PlatformService;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private PlatformService platService;

	@Autowired
	private PlatformRepository platRepo;

	@Autowired
	private HttpSession session;


	@GetMapping("/")
	public String index(HttpSession session, ModelMap userModel) {
		
		Users loggedUser = (Users) session.getAttribute("user");
		System.out.println(loggedUser);

		List<Platform> myPlatforms = platService.listPlatforms();
		userModel.addAttribute("platforms", myPlatforms);
		return "index";
	}

	// LOGIN //
	@GetMapping("/login")
	public String login( @RequestParam(required = false) String logout,
			@RequestParam(required = false) String error, ModelMap modelo) {

		if (error != null) {
			modelo.put("error", "Mail o clave incorrecto");
		} else {
			modelo.addAttribute("error", error);
		}

		if (logout != null) {
			modelo.addAttribute("logout", "Adios");

		} else {
			return "/user/login";
		}

		return "/user/login";

	}

//	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
//	@GetMapping("/logincheck")
//	public String loginresolver() {
//
//		System.out.println("Bien ahi");
//		return "redirect:/";
//	}

}
