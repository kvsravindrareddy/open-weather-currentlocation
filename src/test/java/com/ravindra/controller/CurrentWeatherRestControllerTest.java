package com.ravindra.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

import com.google.common.collect.Iterators;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ravindra.OpenWeatherCurrentlocationApplicationTests;
import com.ravindra.model.CurrentWeather;
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

	private Gson gson = null;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		RestGatewaySupport gateway = new RestGatewaySupport();
		gateway.setRestTemplate(template);
		mockServer = MockRestServiceServer.createServer(gateway);
		gson = new Gson();
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
	public void testSaveWeatherDetails_True() throws Exception {
		String requestJson = "{  \"id\": 8,\r\n" + "    \"cityName\": \"Hyderabad\",\r\n"
				+ "    \"weatherDesc\": \"Hyderabad Weather\",\r\n" + "    \"currTemp\": 25,\r\n"
				+ "    \"tempMin\": 24,\r\n" + "    \"tempMax\": 26,\r\n" + "    \"sunrise\": 125255,\r\n"
				+ "    \"sunset\": 5546\r\n" + "  }";
		CurrentWeather currentWeather = gson.fromJson(requestJson, CurrentWeather.class);
		Mockito.when(currentWeatherRepo.save(currentWeather));
		mockMvc.perform(
				MockMvcRequestBuilders.post("/openmapsrestapi/weather").contentType("application/json;charset=UTF-8")
						.param("city", "Hyderabad").param("savetodatabase", "true").content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testSaveWeatherDetails_False() throws Exception {
		String requestJson = "";
		mockMvc.perform(
				MockMvcRequestBuilders.post("/openmapsrestapi/weather").contentType("application/json;charset=UTF-8")
						.param("city", "Hyderabad").param("savetodatabase", "false").content(requestJson))
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
	public void testGetAllWeatherData_NoData() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/openmapsrestapi/weatherrecords"))
				.andExpect(MockMvcResultMatchers.status().is(204))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8")).andReturn();
	}

	@Test
	public void testGetAllWeatherData() throws Exception {
		String json = "[\r\n" + "  {\r\n" + "    \"id\": 7,\r\n" + "    \"cityName\": \"Donakonda\",\r\n"
				+ "    \"weatherDesc\": \"Donakonda Weather\",\r\n" + "    \"currTemp\": 25,\r\n"
				+ "    \"tempMin\": 24,\r\n" + "    \"tempMax\": 26,\r\n" + "    \"sunrise\": 125255,\r\n"
				+ "    \"sunset\": 5546\r\n" + "  },\r\n" + "  {\r\n" + "    \"id\": 8,\r\n"
				+ "    \"cityName\": \"Hyderabad\",\r\n" + "    \"weatherDesc\": \"Hyderabad Weather\",\r\n"
				+ "    \"currTemp\": 25,\r\n" + "    \"tempMin\": 24,\r\n" + "    \"tempMax\": 26,\r\n"
				+ "    \"sunrise\": 125255,\r\n" + "    \"sunset\": 5546\r\n" + "  },\r\n" + "  {\r\n"
				+ "    \"id\": 9,\r\n" + "    \"cityName\": \"Delhi\",\r\n"
				+ "    \"weatherDesc\": \"Delhi Weather\",\r\n" + "    \"currTemp\": 25,\r\n"
				+ "    \"tempMin\": 26,\r\n" + "    \"tempMax\": 28,\r\n" + "    \"sunrise\": 125255,\r\n"
				+ "    \"sunset\": 5546\r\n" + "  }\r\n" + "]";
		List<CurrentWeather> list = gson.fromJson(json, new TypeToken<List<CurrentWeather>>() {
		}.getType());
		Iterable<CurrentWeather> itr = () -> Iterators.forArray(list.toArray(new CurrentWeather[list.size()]));
		;
		Mockito.when(currentWeatherRepo.findAll()).thenReturn(itr);
		mockMvc.perform(MockMvcRequestBuilders.get("/openmapsrestapi/weatherrecords"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8")).andReturn();
	}

	@Test
	public void testDeleteWeatherRecords() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/openmapsrestapi/deleteweatherrecords")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void testDeleteWeatherRecord() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/openmapsrestapi/deleteweatherrecord").param("id", "8").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
}