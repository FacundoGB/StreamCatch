package com.StreamCatch.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.StreamCatch.app.Service.MovieService;

@Controller
@RequestMapping("/pelicula")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	private final String viewPath = "pelicula/";

}
