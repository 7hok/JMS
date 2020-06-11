package com.java.push.apps;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends JpaRepository<Application,String>{

	public List<Application> findByStatus(Character status);

	// public List<Application> findByProjectIdAndStatus(String id, Character aCTIVE);
    

    
}