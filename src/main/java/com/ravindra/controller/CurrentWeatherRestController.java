package com.ravindra.controller;

import java.io.IOException;

import javax.naming.ServiceUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ravindra.model.CurrentWeather;
import com.ravindra.service.CurrentWeatherService;

import lombok.extern.slf4j.Slf4j;

/**
 * This method created to create the CRUD rest end points for Weather API
 * 
 * @author Veera Shankara Ravindra Reddy Kakarla
 *
 */
@RestController
@RequestMapping("/openmapsrestapi")
@CrossOrigin(origins = "*")
@Slf4j
public class CurrentWeatherRestController {

	@Autowired
	private CurrentWeatherService currentWhetherService;

	/**
	 * Get the Weather API details by calling the Open Weather API URL
	 * http://api.openweathermap.org/data/2.5/weather
	 * 
	 * @return CurrentWeather Weather API Response
	 */
	@GetMapping(value = "/weather")
	public CurrentWeather getCurrentWhether(@RequestParam String city) throws ServiceUnavailableException, IOException {
		log.info("Begin getCurrentWhether()-CurrentWeatherRestController");
		return currentWhetherService.getCurrentWhether(city);
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
	public CurrentWeather saveWeatherDetails(@RequestParam(required = true, name = "city") String city,
			@RequestParam(required = false, name = "savetodatabase") boolean savetodatabase)
			throws ServiceUnavailableException, IOException {
		log.info("Begin saveWeatherDetails()-CurrentWeatherRestController");
		CurrentWeather currentWeather = new CurrentWeather();
		if (savetodatabase) {
			currentWeather = currentWhetherService.saveWeatherDetails(city);
		} else {
			currentWeather = currentWhetherService.getCurrentWhether(city);
		}
		return currentWeather;
	}
	
	/**
	 * Save the CurrentWeather object into PostgreSQL database
	 * 
	 * @param CurrentWeather request
	 */
	@PutMapping(value = "/weathersave")
	public CurrentWeather saveOrUpdateWeatherDetails(@RequestBody CurrentWeather request) {
		log.info("Begin saveWeatherDetailsResponse()-CurrentWeatherRestController");
		return currentWhetherService.saveWeatherDetailsResponse(request);
	}

	/**
	 * Delete Weather object for particular id
	 * 
	 * @param Long id
	 */
	@DeleteMapping(value = "/deleteweatherrecord")
	public void deleteWeatherRecord(@RequestParam Long id) {
		log.info("Begin deleteWeatherRecord()-CurrentWeatherRestController");
		currentWhetherService.deleteWeatherRecord(id);
	}

	/**
	 * Delete all the Weather records
	 */
	@DeleteMapping(value = "/deleteweatherrecords")
	public void deleteWeatherRecords() {
		log.info("Begin deleteWeatherRecords()-CurrentWeatherRestController");
		currentWhetherService.deleteWeatherRecords();
	}

	/**
	 * Get all the Weather information
	 * 
	 * @return Iterable<CurrentWeather>
	 */
	@GetMapping(value = "/weatherrecords")
	public Iterable<CurrentWeather> getAllWeatherData() {
		log.info("Begin getAllWeatherData()-CurrentWeatherRestController");
		return currentWhetherService.getAllWeatherData();
	}
}