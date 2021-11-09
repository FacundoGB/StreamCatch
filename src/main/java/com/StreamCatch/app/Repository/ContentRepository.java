package com.StreamCatch.app.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.StreamCatch.app.Entity.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, String>{
		
	@Query("SELECT u from Content u WHERE u.id = :id")
	public Optional<Content> findById(@Param("id") String id);
}
