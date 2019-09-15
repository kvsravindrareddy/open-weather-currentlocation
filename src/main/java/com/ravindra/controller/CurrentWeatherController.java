package com.ravindra.controller;

import java.io.IOException;

import javax.naming.ServiceUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ravindra.model.CurrentWeather;
import com.ravindra.service.CurrentWeatherService;

import lombok.extern.slf4j.Slf4j;

/**
 * This class created to test the Weather API with Spring Boot Web MVC using
 * Thymeleaf template engine
 * 
 * @author Veera Shankara Ravindra Reddy Kakarla
 *
 */
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/openmaps")
@Slf4j
public class CurrentWeatherController {

	@Autowired
	private CurrentWeatherService currentWeatherService;

	/**
	 * Get the Weather API details by calling the Open Weather API URL
	 * http://api.openweathermap.org/data/2.5/weather
	 * 
	 * @return String Weather API Response
	 */
	@GetMapping(value = "/weather")
	public String getCurrentWeather(@RequestParam(value = "city", required = true) String city) {
		log.debug("Current weather search city : " + city);
		// model.addAttribute("currentWeather",
		// currentWeatherService.getCurrentWhether(city));
		return "weather";
	}

	/**
	 * Save the Weather API details the into PostgreSQL database by giving the City
	 * name as the input parameter
	 * 
	 * @param String city
	 * @throws ServiceUnavailableException
	 * @throws IOException
	 */
	@PostMapping(value = "/weather")
	public void saveWeatherDetails(@RequestParam String city) throws ServiceUnavailableException, IOException {
		log.info("Begin saveWeatherDetails()-CurrentWeatherController");
		currentWeatherService.saveWeatherDetails(city);
	}

	/**
	 * Save the CurrentWeather object into PostgreSQL database
	 * 
	 * @param CurrentWeather request
	 */
	@PostMapping(value = "/weathersave")
	public void saveWeatherDetailsResponse(@RequestBody CurrentWeather request) {
		log.info("Begin saveWeatherDetailsResponse()-CurrentWeatherController");
		currentWeatherService.saveWeatherDetailsResponse(request);
	}

	/**
	 * Delete Weather object for particular id
	 * 
	 * @param Long id
	 */
	@DeleteMapping(value = "/deleteweatherrecord")
	public void deleteWeatherRecord(@RequestParam Long id) {
		log.info("Begin deleteWeatherRecord()-CurrentWeatherController");
		currentWeatherService.deleteWeatherRecord(id);
	}

	/**
	 * Delete all the Weather records
	 */
	@DeleteMapping(value = "/deleteweatherrecords")
	public void deleteWeatherRecords() {
		log.info("Begin deleteWeatherRecords()-CurrentWeatherController");
		currentWeatherService.deleteWeatherRecords();
	}

	/**
	 * Get all the Weather information
	 * 
	 * @return Iterable<CurrentWeather>
	 */
	@GetMapping(value = "/weatherrecords")
	public String getAllWeatherData(Model model) {
		log.info("Begin getAllWeatherData()-CurrentWeatherController");
		model.addAttribute("weatherreports", currentWeatherService.getAllWeatherData());
		return "weather";
	}
}