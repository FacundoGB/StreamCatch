package com.StreamCatch.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.StreamCatch.app.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
