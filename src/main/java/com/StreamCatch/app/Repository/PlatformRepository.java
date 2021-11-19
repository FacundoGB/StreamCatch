package com.StreamCatch.app.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.StreamCatch.app.Entity.Platform;
import com.StreamCatch.app.Entity.Users;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, String>{
	
	
	public List<Platform> findByUsers(Users users);
	
	public Optional<Platform> findByName(String name);
	
//	@Query("SELECT pu.platform_id FROM Platform_Users pu WHERE pu.users_id= :id")
//	public List<Platform> userPlatforms(@Param("id") String id);
	
//	@Query("SELECT p FROM platform p WHERE p.users.id = :id")
//	public List<Platform> dispalyPlatforms(@Param("id") String id);
	
//	@Query("SELECT p FROM Platform p JOIN platform_users WHERE p.users.id = :id")
//	public List<Platform> dispalyPlatforms(@Param("id") String id);	

}
