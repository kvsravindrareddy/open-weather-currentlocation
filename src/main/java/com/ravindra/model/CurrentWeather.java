package com.ravindra.model;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * This is the Entity and Model class for the Weather API Response and Database
 * object
 * 
 * @author Veera Shankara Ravindra Reddy Kakarla
 *
 */
@Setter
@Getter
@Entity(name = "WEATHER_TB")
public class CurrentWeather {
	@Id
	@GeneratedValue
	private Long id;
	// City name
	private String cityName;
	// weather description
	private String weatherDesc;
	// current temparature
	private Double currTemp;
	private Double tempMin;
	private Double tempMax;
	private BigInteger sunrise;
	private BigInteger sunset;
}