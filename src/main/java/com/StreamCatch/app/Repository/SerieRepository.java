package com.StreamCatch.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.StreamCatch.app.Entity.Serie;


@Repository
public interface SerieRepository extends JpaRepository<Serie, String>{

}
