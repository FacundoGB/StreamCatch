package com.StreamCatch.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.StreamCatch.app.Service.SerieService;

@Controller
@RequestMapping("/serie")
public class SerieController {
	
	@Autowired
	private SerieService serieService;
	private final String viewPath = "serie/";

}
