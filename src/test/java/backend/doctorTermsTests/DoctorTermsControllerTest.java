package backend.doctorTermsTests;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class) //This is not mandatory
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // Secure false is required to by pass security for Test Cases
@ContextConfiguration //This is also not mandatory just to remove annoying warning, i added it
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)//This annotation was required to run it successfully
@DisplayName("DoctorTermsControllerTest - SpringBootTest")
public class DoctorTermsControllerTest {
	
	private static final String URL_PREFIX = "/doctorterms";
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype());

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
//	@Test
//	public void testGetDefinedForAdmin() throws Exception {
//		mockMvc.perform(get(URL_PREFIX + "/definedterms-admin/1/1")).andExpect(status().isOk())
//		.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(2)))
//		.andExpect(jsonPath("$.[*].id").value(hasItem(1)))
//		.andExpect(jsonPath("$.[*].doctorId").value(hasItem(1)))
//		.andExpect(jsonPath("$.[*].pharmacyId").value(hasItem(1)))
//		.andExpect(jsonPath("$.[*].start").value(hasItem("2021-06-05T16:00:00")))
//		.andExpect(jsonPath("$.[*].finish").value(hasItem("2021-06-05T17:00:00")));
//	}
//	
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testGoodCreateForAdmin() throws Exception {
//		String jsonString="{\"doctorId\": \"1\", \"pharmacyId\": \"3\", \"start\": \"2021-06-05T18:00:00\", \"finish\": \"2021-06-05T19:00:00\" } ";
//		mockMvc.perform(post(URL_PREFIX + "/createnew-admin").contentType(MediaType.APPLICATION_JSON).content(jsonString)).andExpect(status().isOk())
//		.andExpect(content().contentType(contentType))
//		.andExpect(jsonPath("$.[*].id").value(hasItem(6)))
//		.andExpect(jsonPath("$.[*].doctorId").value(hasItem(1)))
//		.andExpect(jsonPath("$.[*].pharmacyId").value(hasItem(3)));
//	}
//	
//	@Test
//	public void testTakenCreateForAdmin() throws Exception {
//		String jsonString="{\"doctorId\": \"1\", \"pharmacyId\": \"3\", \"start\": \"2021-06-05T18:00:00\", \"finish\": \"2021-06-05T19:00:00\" } ";
//		MvcResult res = mockMvc.perform(post(URL_PREFIX + "/createnew-admin").contentType(MediaType.APPLICATION_JSON).content(jsonString)).andExpect(status().isOk()).andReturn();
//		assertEquals("Taken term", res.getResponse().getContentAsString());
//	}
//	
//	@Test
//	public void testBadWHCreateForAdmin() throws Exception {
//		String jsonString="{\"doctorId\": \"1\", \"pharmacyId\": \"3\", \"start\": \"2021-06-05T01:00:00\", \"finish\": \"2021-06-05T02:00:00\" } ";
//		MvcResult res = mockMvc.perform(post(URL_PREFIX + "/createnew-admin").contentType(MediaType.APPLICATION_JSON).content(jsonString)).andExpect(status().isOk()).andReturn();
//		assertEquals("Not in your working hours", res.getResponse().getContentAsString());
//	}

}
