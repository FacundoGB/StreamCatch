package com.StreamCatch.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.StreamCatch.app.Entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {

	@Query("SELECT u FROM Users u WHERE u.email = :email")
	public Users searchByEmail(@Param("email") String email);
}
