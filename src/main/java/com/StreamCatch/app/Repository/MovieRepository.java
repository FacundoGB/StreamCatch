package com.StreamCatch.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.StreamCatch.app.Entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

}
