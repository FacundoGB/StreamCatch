package com.StreamCatch.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.StreamCatch.app.Service.UserService;

@SpringBootApplication
public class StreamCatchApplication {

	
//	@Autowired
//	@Qualifier("userService")
//	public UserService userService;
//	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
//	}
//	
	public static void main(String[] args) {
		SpringApplication.run(StreamCatchApplication.class, args);
	}

}
