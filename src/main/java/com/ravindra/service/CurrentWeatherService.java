package com.ravindra.service;

import java.io.IOException;

import javax.naming.ServiceUnavailableException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ravindra.model.CurrentWeather;
import com.ravindra.repo.CurrentWeatherRepo;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author VeeraShankara
 *
 */
@Service
@Slf4j
public class CurrentWeatherService {

	@Value("${openwhethermap.current}")
	private String currentWhetherUrl;

	@Value("${openwhethermap.subscriptionkey}")
	private String subscriptionkey;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CurrentWeatherRepo currentWeatherRepo;

	/**
	 * Get the Weather information for the given city
	 * 
	 * @param String city name
	 * @return CurrentWeather
	 * @throws IOException 
	 * @throws ServiceUnavailableException
	 */
	public CurrentWeather getCurrentWhether(String city) throws ServiceUnavailableException, IOException {
		log.info("Begin getCurrentWhether()-CurrentWeatherService");
		CurrentWeather currentWeather = new CurrentWeather();
		currentWeather.setCityName(city);
		String serviceUrl = uriBuilder(city);
		String weatherResponse = restTemplate.getForObject(serviceUrl, String.class);
		JSONObject responseJson = new JSONObject(weatherResponse);
		JSONArray weatherJsonArr = responseJson.getJSONArray("weather");
		currentWeather.setWeatherDesc(weatherJsonArr.getJSONObject(0).getString("description"));
		JSONObject mainObj = responseJson.getJSONObject("main");
		currentWeather.setCurrTemp(mainObj.getDouble("temp"));
		currentWeather.setTempMin(mainObj.getDouble("temp_min"));
		currentWeather.setTempMax(mainObj.getDouble("temp_max"));
		JSONObject sysObj = responseJson.getJSONObject("sys");
		currentWeather.setSunrise(sysObj.getBigInteger("sunrise"));
		currentWeather.setSunset(sysObj.getBigInteger("sunset"));
		log.info("End getCurrentWhether()-CurrentWeatherService");
		return currentWeather;
	}

	/**
	 * URI component builder to build the Weather API with city and subscription key
	 * details
	 * 
	 * @param String currentCity
	 * @return String weather API URL with city and subscription key
	 * @throws ServiceUnavailableException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	private String uriBuilder(String currentCity) throws ServiceUnavailableException, IOException {
		log.info("Begin uriBuilder()-CurrentWeatherService");
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(new HttpGet(currentWhetherUrl));
		int statusCode = 0;
		statusCode = response.getStatusLine().getStatusCode();
		log.debug("Weather service status code : " + statusCode);
		String serviceUrl = "";
		if (statusCode == 200) {
			serviceUrl = UriComponentsBuilder.fromHttpUrl(currentWhetherUrl).queryParam("q", currentCity)
			.queryParam("APPID", subscriptionkey).build().toUri().toString();
		}
		else
		{
			log.error("ServiceNotFoundException occured in getCurrentWhether " + statusCode);
			throw new ServiceUnavailableException(
					"Open Weather API Service is not available at this time. Please try again after sometime.");
		}
		return serviceUrl;
	}

	/**
	 * Save the Weather information for the given city
	 * 
	 * @param String city
	 * @throws ServiceUnavailableException
	 * @throws IOException 
	 */
	public void saveWeatherDetails(String city) throws ServiceUnavailableException, IOException {
		log.info("Begin saveWeatherDetails()-CurrentWeatherService");
		CurrentWeather currentWeatcher = getCurrentWhether(city);
		currentWeatherRepo.save(currentWeatcher);
	}

	/**
	 * Save the Weather object with all the Weather information details
	 * 
	 * @param CurrentWeather request
	 */
	public void saveWeatherDetailsResponse(CurrentWeather request) {
		log.info("Begin saveWeatherDetailsResponse()-CurrentWeatherService");
		currentWeatherRepo.save(request);
	}

	/**
	 * Delete Weather object for a given id from Database
	 * 
	 * @param Long id
	 */
	public void deleteWeatherRecord(Long id) {
		log.info("Begin deleteWeatherRecord()-CurrentWeatherService");
		currentWeatherRepo.deleteById(id);
	}

	/**
	 * Delete all stored Weather data from Database
	 */
	public void deleteWeatherRecords() {
		log.info("Begin deleteWeatherRecords()-CurrentWeatherService");
		currentWeatherRepo.deleteAll();
	}

	/**
	 * Get all the Weather data from the Database
	 * 
	 * @return List<CurrentWeather>
	 */
	public Iterable<CurrentWeather> getAllWeatherData() {
		log.info("Begin getAllWeatherData()-CurrentWeatherService");
		return currentWeatherRepo.findAll();
	}
}