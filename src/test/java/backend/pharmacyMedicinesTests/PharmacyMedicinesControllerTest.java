package backend.pharmacyMedicinesTests;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PharmacyMedicinesControllerTest {

	private static final String URL_PREFIX = "/pharmacy-medicines";
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype());

	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testGetAll() throws Exception {
				
		mockMvc.perform(get(URL_PREFIX + "/all")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(6)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(1)))
		.andExpect(jsonPath("$.[*].pharmacy.id").value(hasItem(1)))
		.andExpect(jsonPath("$.[*].medicine.id").value(hasItem(1)))
		.andExpect(jsonPath("$.[*].price").value(hasItem(100.0)))
		.andExpect(jsonPath("$.[*].quantity").value(hasItem(100)))
		.andExpect(jsonPath("$.[*].startDate").value(hasItem(1621893600000l)))
		.andExpect(jsonPath("$.[*].endDate").value(hasItem(1640386800000l)));
		
	}
	
	@Test
	public void testGetPMFromPharmacyId() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/get-medicines/1")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.[*].id").value(hasItem(2)))
		.andExpect(jsonPath("$.[*].pharmacy.id").value(hasItem(1)))
		.andExpect(jsonPath("$.[*].medicine.id").value(hasItem(2)))
		.andExpect(jsonPath("$.[*].price").value(hasItem(120.0)))
		.andExpect(jsonPath("$.[*].quantity").value(hasItem(100)));
	}
	
	@Test
	public void testGetPMFromMedicineId() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/get-pharmacies/1")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.[*].id").value(hasItem(6)))
		.andExpect(jsonPath("$.[*].pharmacy.id").value(hasItem(3)))
		.andExpect(jsonPath("$.[*].medicine.id").value(hasItem(1)))
		.andExpect(jsonPath("$.[*].price").value(hasItem(200.0)))
		.andExpect(jsonPath("$.[*].quantity").value(hasItem(26)));
	}
	
	@Test
	public void testGetPMFromMedicineName() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/get-medicines-by-name/Lek1")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.[*].id").value(hasItem(6)))
		.andExpect(jsonPath("$.[*].pharmacy.id").value(hasItem(3)))
		.andExpect(jsonPath("$.[*].medicine.id").value(hasItem(1)))
		.andExpect(jsonPath("$.[*].price").value(hasItem(200.0)))
		.andExpect(jsonPath("$.[*].quantity").value(hasItem(26)));
	}
	
	@Test
	public void testGetPMFromPharmacyIdAndMedicineName() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/get-medicines-by-name/3/Lek1")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.[*].id").value(hasItem(6)))
		.andExpect(jsonPath("$.[*].pharmacy.id").value(hasItem(3)))
		.andExpect(jsonPath("$.[*].medicine.id").value(hasItem(1)))
		.andExpect(jsonPath("$.[*].price").value(hasItem(200.0)))
		.andExpect(jsonPath("$.[*].quantity").value(hasItem(26)));
	}
	
	@Test
	public void testGetPMByIds() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/get-by-ids/1/3")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id").value(3))
		.andExpect(jsonPath("$.pharmacy.id").value(1))
		.andExpect(jsonPath("$.medicine.id").value(3))
		.andExpect(jsonPath("$.price").value(90.0))
		.andExpect(jsonPath("$.quantity").value(100));
	}
}
