package com.ravindra.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ravindra.model.CurrentWeather;

/**
 * Crud repository interface created to store the Weather API database into
 * PostgreSQL database
 * 
 * @author Veera Shankara Ravindra Reddy Kakarla
 *
 */
@Repository
public interface CurrentWeatherRepo extends CrudRepository<CurrentWeather, Long> {

}