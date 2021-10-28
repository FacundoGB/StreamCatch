package com.StreamCatch.app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StreamCatch.app.Repository.PlatformRepository;

@Service
public class PlatformService {

	@Autowired
	private PlatformRepository repo;
}
