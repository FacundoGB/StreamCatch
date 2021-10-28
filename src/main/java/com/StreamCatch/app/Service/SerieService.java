package com.StreamCatch.app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StreamCatch.app.Repository.SerieRepository;

@Service
public class SerieService {

	@Autowired
	private SerieRepository repo;
}
