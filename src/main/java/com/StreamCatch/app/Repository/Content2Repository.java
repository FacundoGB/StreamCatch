package com.StreamCatch.app.Repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.StreamCatch.app.Entity.Content2;

@Repository
public interface Content2Repository  extends JpaRepository<Content2, String>{
 //Custom query
 @Query(value = "select * from Content2 c where c.name like %:keyword%", nativeQuery = true)
 List<Content2> findByKeyword(@Param("keyword") String keyword);
}