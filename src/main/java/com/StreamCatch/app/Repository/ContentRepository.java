package com.StreamCatch.app.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.StreamCatch.app.Entity.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, String>{
		
	@Query("SELECT u FROM Content u WHERE u.id = :id")
	public Optional<Content> findById(@Param("id") String id);
	
	@Query("SELECT c.id FROM Content c WHERE c.name like %:keyword%")
	public Optional<Content> findByKeyword(@Param("keyword") String keyword);
	
	@Query(value = "SELECT * FROM StreamCatch.content WHERE StreamCatch.content.name LIKE '%q%'", nativeQuery=true)
	List<Content> findByName(@Param("q")String q);

}





