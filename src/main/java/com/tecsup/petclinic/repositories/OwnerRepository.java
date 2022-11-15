package com.tecsup.petclinic.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tecsup.petclinic.entities.Owner;

/**
 * 
 * @author jgomezm
 *
 */
@Repository
public interface OwnerRepository 
	extends CrudRepository<Owner, Long> {

	// Fetch pets by name
	List<Owner> findByName(String first_name);

	// Fetch pets by typeId
	List<Owner> findByLast(String last_name);

}

