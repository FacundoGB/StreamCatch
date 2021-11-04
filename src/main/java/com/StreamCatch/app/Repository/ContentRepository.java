package com.StreamCatch.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.StreamCatch.app.Entity.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, String>{
	
}
