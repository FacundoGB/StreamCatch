package com.StreamCatch.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.StreamCatch.app.Entity.Platform;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, String>{


}
