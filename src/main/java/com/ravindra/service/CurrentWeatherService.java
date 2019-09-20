package com.ravindra.service;

import java.io.IOException;

import javax.naming.ServiceUnavailableException;

import org.apache.commons.collections4.IterableUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ravindra.exception.InvalidInputException;
import com.ravindra.exception.NoDataFoundException;
import com.ravindra.exception.ServiceNotFoundException;
import com.ravindra.model.CurrentWeather;
import com.ravindra.repo.CurrentWeatherRepo;

import lombok.extern.slf4j.Slf4j;

/**
 * This is the service class. All the business validations done here.
 * 
 * @author Veera Shankara Ravindra Reddy Kakarla
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
	public CurrentWeather getCurrentWhether(String city) throws IOException {
		log.info("Begin getCurrentWhether()-CurrentWeatherService");
		if (city.length() < 3) {
			throw new InvalidInputException("Please provide City name atleast 3 characters");
		}
		CloseableHttpClient client = HttpClientBuilder.create().build();
		String serviceUrl = uriBuilder(city);
		log.debug("Weather serivce URL : " + serviceUrl);
		HttpResponse response = client.execute(new HttpGet(serviceUrl));
		int statusCode = 0;
		statusCode = response.getStatusLine().getStatusCode();
		log.debug("Weather service status code " + statusCode);
		CurrentWeather currentWeather = null;
		if (statusCode == 200) {
			currentWeather = new CurrentWeather();
			currentWeather.setId(0L);
			currentWeather.setCityName(city);
			String weatherResponse = restTemplate.getForObject(serviceUrl, String.class);
			JSONObject responseJson = new JSONObject(weatherResponse);
			JSONArray weatherJsonArr = responseJson.getJSONArray("weather");
			currentWeather.setWeatherDesc(weatherJsonArr.getJSONObject(0).getString("description"));
			JSONObject mainObj = responseJson.getJSONObject("main");
			currentWeather.setCurrTemp(convertKelvinToCelsius(mainObj.getDouble("temp")));
			currentWeather.setTempMin(convertKelvinToCelsius(mainObj.getDouble("temp_min")));
			currentWeather.setTempMax(convertKelvinToCelsius(mainObj.getDouble("temp_max")));
			JSONObject sysObj = responseJson.getJSONObject("sys");
			currentWeather.setSunrise(sysObj.getBigInteger("sunrise"));
			currentWeather.setSunset(sysObj.getBigInteger("sunset"));
		} else {
			log.error("ServiceNotFoundException occured in getCurrentWhether " + statusCode);
			throw new ServiceNotFoundException(
					"Open Weather API Service is not available at this time. Please try again after sometime.");
		}
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
	private String uriBuilder(String currentCity) throws IOException {
		log.info("Begin uriBuilder()-CurrentWeatherService");
		return UriComponentsBuilder.fromHttpUrl(currentWhetherUrl).queryParam("q", currentCity)
				.queryParam("APPID", subscriptionkey).build().toUri().toString();
	}

	/**
	 * Convert Kelvin temparature which is returned by Open Weather API and convert
	 * it to Celsius
	 * 
	 * @param kelvin
	 * @return
	 */
	private Double convertKelvinToCelsius(Double kelvin) {
		return kelvin - 273.15;
	}

	/**
	 * Save the Weather information for the given city
	 * 
	 * @param String city
	 * @throws ServiceUnavailableException
	 * @throws IOException
	 */
	public CurrentWeather saveWeatherDetails(String city) throws ServiceUnavailableException, IOException {
		log.info("Begin saveWeatherDetails()-CurrentWeatherService");
		CurrentWeather currentWeatcher = getCurrentWhether(city);
		return currentWeatherRepo.save(currentWeatcher);
	}

	/**
	 * Save the Weather object with all the Weather information details
	 * 
	 * @param CurrentWeather request
	 */
	public CurrentWeather saveWeatherDetailsResponse(CurrentWeather request) {
		log.info("Begin saveWeatherDetailsResponse()-CurrentWeatherService");
		return currentWeatherRepo.save(request);
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
		Iterable<CurrentWeather> itr = currentWeatherRepo.findAll();
		int size = IterableUtils.size(itr);
		if (size < 1) {
			throw new NoDataFoundException("No Weather data to retrive from Database");
		}
		return itr;
	}
}
