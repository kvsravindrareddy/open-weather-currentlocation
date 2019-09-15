package com.ravindra.controller;

import java.io.IOException;

import javax.naming.ServiceUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ravindra.model.CurrentWeather;
import com.ravindra.service.CurrentWeatherService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author VeeraShankara
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
	 * 
	 * @return
	 * @throws ServiceUnavailableException
	 * @throws IOException 
	 */
	@GetMapping(value = "/weather")
	public CurrentWeather getCurrentWhether(@RequestParam String city) throws ServiceUnavailableException, IOException {
		log.info("Begin getCurrentWhether()-CurrentWeatherRestController");
		return currentWhetherService.getCurrentWhether(city);
	}

	@PostMapping(value = "/weather")
	public void saveWeatherDetails(@RequestParam String city) throws ServiceUnavailableException, IOException {
		log.info("Begin saveWeatherDetails()-CurrentWeatherRestController");
		currentWhetherService.saveWeatherDetails(city);
	}

	@PostMapping(value = "/weathersave")
	public void saveWeatherDetailsResponse(@RequestBody CurrentWeather request) {
		log.info("Begin saveWeatherDetailsResponse()-CurrentWeatherRestController");
		currentWhetherService.saveWeatherDetailsResponse(request);
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