package com.StreamCatch.app.Service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.StreamCatch.app.Repository.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repo;
}
