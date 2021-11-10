package com.StreamCatch.app.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.StreamCatch.app.Repository.ContentRepository;

public class ContentService {
	
	@Autowired
	private ContentRepository repo;
}
