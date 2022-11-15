package com.tecsup.petclinic.controllers;

import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dto.OwnerDTO;
/**
 * 
 */
@AutoConfigureMockMvc
@SpringBootTest
public class OwnerControllerTest {

	private static final Logger logger 
			= LoggerFactory.getLogger(OwnerControllerTest.class);

    private static final ObjectMapper om = new ObjectMapper();
    
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testFindAllOwners() throws Exception {

		//int SIZE = 216;
		int ID_FIRST = 1;
		//int ID_LAST = 332;  

		this.mockMvc.perform(get("/owners"))
					.andExpect(status().isOk()) // HTTP 200
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
								    // ACTUAL      EXPECTED 
					//.andExpect(jsonPath("$", hasSize(SIZE)))
					.andExpect(jsonPath("$[0].id", is(ID_FIRST)));
					//.andExpect(jsonPath("$[212].id", is(ID_LAST)));
	}
	

	/**
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testFindOwnerOK() throws Exception {

		int ID_SEARCH = 1;
		String FIRST_OWNER = "George";
		String LAST_OWNER = "Franklin";
		String ADDRESS_OWNER = "110 W. Liberty St.";
		String CITY_OWNER = "Madison";
		String TELEPHONE_OWNER = "6085551023";

		/*
		 {
		    "id": 1,
		    "firstname": "George",
		    "lastname": "Franklin",
		    "address": "110 W. Liberty St.",
		    "city": "Madison",
		    "telephone": "6085551023"
		}
		 */
		
		mockMvc.perform(get("/owners/" + ID_SEARCH))  // Finding object with ID = 1
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.firstname", is(FIRST_OWNER)))
				.andExpect(jsonPath("$.lastname", is(LAST_OWNER)))
				.andExpect(jsonPath("$.address", is(ADDRESS_OWNER)))
				.andExpect(jsonPath("$.city", is(CITY_OWNER)))
				.andExpect(jsonPath("$.telephone", is(TELEPHONE_OWNER)));

	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindOwnerKO() throws Exception {

		int ID_SEARCH = 666;

		
		mockMvc.perform(get("/owners/" + ID_SEARCH)) // Finding object with ID = 666
				.andExpect(status().isNotFound());

	}
	
	/**
	 * @throws Exception
	 */
	
	@Test
    public void testCreateOwner() throws Exception {
		
    	String FIRST_OWNER = "BeethovenY";
    	String LAST_OWNER = "Martinez";
    	String ADDRESS_OWNER = "Los Claveles";
    	String CITY_OWNER = "San Juan";
    	String TELEPHONE_OWNER = "956874568";
		
		OwnerDTO newOwner = new OwnerDTO(FIRST_OWNER, LAST_OWNER, ADDRESS_OWNER, CITY_OWNER, TELEPHONE_OWNER);
	    
		logger.info(newOwner.toString());
		logger.info(om.writeValueAsString(newOwner));
	    
	    mockMvc.perform(post("/owners")
	            .content(om.writeValueAsString(newOwner))
	            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(status().isCreated())
	            .andExpect(jsonPath("$.firstname", is(FIRST_OWNER)))
	            .andExpect(jsonPath("$.lastname", is(LAST_OWNER)))
	            .andExpect(jsonPath("$.address", is(ADDRESS_OWNER)))
	    		.andExpect(jsonPath("$.city", is(CITY_OWNER)))
	    		.andExpect(jsonPath("$.telephone", is(TELEPHONE_OWNER)));
    
	}
    

    /**
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteOwner() throws Exception {

    	String FIRST_OWNER = "Beethoven3";
    	String LAST_OWNER = "Saavedra";
    	String ADDRESS_OWNER = "Los Girasoles";
    	String CITY_OWNER = "San Juan de Miraflores";
    	String TELEPHONE_OWNER = "966874568";
		
		OwnerDTO newOwner = new OwnerDTO(FIRST_OWNER, LAST_OWNER, ADDRESS_OWNER, CITY_OWNER, TELEPHONE_OWNER);
		
		ResultActions mvcActions = mockMvc.perform(post("/owners")
	            .content(om.writeValueAsString(newOwner))
	            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(status().isCreated());
	            
		String response = mvcActions.andReturn().getResponse().getContentAsString();

		Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/owners/" + id ))
                 /*.andDo(print())*/
                .andExpect(status().isOk());
    }
    
}
    
