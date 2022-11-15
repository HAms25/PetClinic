package com.tecsup.petclinic.services;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;

@SpringBootTest
public class OwnerServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(OwnerServiceTest.class);

	@Autowired
	private OwnerService ownerService;

	@Test
	public void testFindOwnerById() {

		long ID = 1;
		String FIRST_NAME = "George";
		Owner owner = null;
		
		try {
			owner = ownerService.findById(ID);
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}
		
		logger.info("" + owner);
		assertThat(owner.getFirst_name(), is(FIRST_NAME));

	}


	@Test
	public void testFindOwnerByName() {

		String FIND_NAME = "George";
		int SIZE_EXPECTED = 1;

		List<Owner> owners = ownerService.findByName(FIND_NAME);

		assertThat(owners.size(), is(SIZE_EXPECTED));
	}


	/**
	 *  To get ID generate , you need 
	 *  setup in id primary key in your
	 *  entity this annotation :
	 *  	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 */
	@Test
	public void testCreateOwner() {

		String OWNER_FIRST_NAME = "Hilari";
		String OWNER_LAST_NAME = "Martinez";
		String OWNER_ADDRESS = "LOS CLAVELES";
		String OWNER_CITY = "LOS ANGELES";
		String OWNER_TELEPHONE= "999999999";

		Owner owner = new Owner(OWNER_FIRST_NAME, OWNER_LAST_NAME, OWNER_ADDRESS, OWNER_CITY, OWNER_TELEPHONE);
		
		Owner ownerCreated = ownerService.create(owner);
		
		logger.info("OWNER CREATED :" + ownerCreated);

		//          ACTUAL                 , EXPECTED 
		assertThat(ownerCreated.getId()      , notNullValue());
		assertThat(ownerCreated.getFirst_name()    , is(OWNER_FIRST_NAME));
		assertThat(ownerCreated.getLast_name() , is(OWNER_LAST_NAME));
		assertThat(ownerCreated.getAddress()  , is(OWNER_ADDRESS));
		assertThat(ownerCreated.getCity()  , is(OWNER_CITY));
		assertThat(ownerCreated.getTelephone()  , is(OWNER_TELEPHONE));

	}

	
	/**
	 * 
	 */
	@Test
	public void testUpdateOwner() {

		String OWNER_FIRST_NAME = "Carlos";
		String OWNER_LAST_NAME = "Estaban";
		String OWNER_ADDRESS = "2335 Independence La.";
		String OWNER_CITY = "Waunakee";
		String OWNER_TELEPHONE= "6085555487";
		long create_id = -1;

		String UP_OWNER_FIRST_NAME = "Anguie";
		String UP_OWNER_LAST_NAME = "Saavedra";
		String UP_OWNER_ADDRESS = "LOS Girasoles";
		String UP_OWNER_CITY = "LOS ANGEL";
		String UP_OWNER_TELEPHONE= "999999989";

		Owner owner = new Owner(create_id, OWNER_FIRST_NAME, OWNER_LAST_NAME, OWNER_ADDRESS, OWNER_CITY, OWNER_TELEPHONE);

		// Create record
		logger.info(">" + owner);
		Owner ownerCreated = ownerService.create(owner);
		logger.info(">>" + ownerCreated);

		create_id = ownerCreated.getId();

		// Prepare data for update
		ownerCreated.setFirst_name(UP_OWNER_FIRST_NAME);
		ownerCreated.setLast_name(UP_OWNER_LAST_NAME);
		ownerCreated.setAddress(UP_OWNER_ADDRESS);
		ownerCreated.setCity(UP_OWNER_CITY);
		ownerCreated.setTelephone(UP_OWNER_TELEPHONE);

		// Execute update
		Owner upgradeOwner = ownerService.update(ownerCreated);
		logger.info(">>>>" + upgradeOwner);

		//        ACTUAL       EXPECTED
		assertThat(create_id ,notNullValue());
		assertThat(upgradeOwner.getId(), is(create_id));
		assertThat(upgradeOwner.getFirst_name(), is(UP_OWNER_FIRST_NAME));
		assertThat(upgradeOwner.getLast_name(), is(UP_OWNER_LAST_NAME));
		assertThat(upgradeOwner.getAddress(), is(UP_OWNER_ADDRESS));
		assertThat(upgradeOwner.getCity(), is(UP_OWNER_CITY));
		assertThat(upgradeOwner.getTelephone(), is(UP_OWNER_TELEPHONE));
	}

	/**
	 * 
	 */
	@Test
	public void testDeleteOwner() {

		String OWNER_FIRST_NAME = "Anguie";
		String OWNER_LAST_NAME = "Saavedra";
		String OWNER_ADDRESS = "LOS Girasoles";
		String OWNER_CITY = "LOS ANGEL";
		String OWNER_TELEPHONE= "999999989";

		Owner owner = new Owner(15, OWNER_FIRST_NAME, OWNER_LAST_NAME, OWNER_ADDRESS, OWNER_CITY, OWNER_TELEPHONE);
		owner = ownerService.create(owner);
		logger.info("" + owner);

		try {
			ownerService.delete(owner.getId());
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}
			
		try {
			ownerService.findById(owner.getId());
			fail("Owner id = " + owner.getId() + " has not delete");
		} catch (OwnerNotFoundException e) {
		} 				

	}
}