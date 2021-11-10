package com.StreamCatch.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.StreamCatch.app.Service.PlatformService;

@Controller
@RequestMapping("/platform")
public class PlatformController {
	
	@Autowired
	private PlatformService platService;
	private final String viewPath = "platform/";

}
