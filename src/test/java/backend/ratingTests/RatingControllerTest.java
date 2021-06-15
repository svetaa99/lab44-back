package backend.ratingTests;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class RatingControllerTest {
	
	private static final String URL_PREFIX = "/ratings";
	
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
	public void testGetRating() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/get-rating/1/1")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType)).andExpect(jsonPath("$").value(5.0));
	}
	
	@Test
	public void testBadFirstGetRating() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/get-rating/200/1")).andExpect(status().isBadRequest());
	}
	
	@Test
	public void testSecondFirstGetRating() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/get-rating/1/200")).andExpect(status().isBadRequest());
	}
}
