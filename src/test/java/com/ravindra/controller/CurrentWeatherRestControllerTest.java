package com.ravindra.controller;

import java.io.ByteArrayInputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestGatewaySupport;
import org.springframework.web.context.WebApplicationContext;

import com.ravindra.OpenWeatherCurrentlocationApplicationTests;
import com.ravindra.repo.CurrentWeatherRepo;

public class CurrentWeatherRestControllerTest extends OpenWeatherCurrentlocationApplicationTests {
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@MockBean(name = "currentWeatherRepo")
	private CurrentWeatherRepo currentWeatherRepo;

	@Autowired
	private RestTemplate template;

	private MockRestServiceServer mockServer;

	ByteArrayInputStream stream;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		RestGatewaySupport gateway = new RestGatewaySupport();
		gateway.setRestTemplate(template);
		mockServer = MockRestServiceServer.createServer(gateway);
	}

	@Test
	public void testGetCurrentWhether() throws Exception {
		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get("/openmapsrestapi/weather").param("city", "Hyderabad"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		MvcResult response = result.andReturn();
		Assert.assertNotNull(response);
	}

	@Test
	public void testSaveWeatherDetails() throws Exception {
		String requestJson = "";
		mockMvc.perform(MockMvcRequestBuilders.post("/openmapsrestapi/weather")
				.contentType("application/json;charset=UTF-8").param("city", "Hyderabad").content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testSaveWeatherDetailsResponse() throws Exception {
		String requestJson = "{\r\n" + "  \"id\": 1,\r\n" + "  \"cityName\": \"Hyderabad\",\r\n"
				+ "  \"weatherDesc\": \"mist\",\r\n" + "  \"currTemp\": 24.54000000000002,\r\n"
				+ "  \"tempMin\": 24,\r\n" + "  \"tempMax\": 25,\r\n" + "  \"sunrise\": 1568507650,\r\n"
				+ "  \"sunset\": 1568551752\r\n" + "}";
		mockMvc.perform(MockMvcRequestBuilders.post("/openmapsrestapi/weathersave")
				.contentType("application/json;charset=UTF-8").content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetAllWeatherData() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/openmapsrestapi/weatherrecords"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8")).andReturn();
	}

	@Test
	public void testDeleteWeatherRecords() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/openmapsrestapi/deleteweatherrecords"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8")).andReturn();
	}
	
	@Test
	public void testDeleteWeatherRecord() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/openmapsrestapi/deleteweatherrecord"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8")).andReturn();
	}
}