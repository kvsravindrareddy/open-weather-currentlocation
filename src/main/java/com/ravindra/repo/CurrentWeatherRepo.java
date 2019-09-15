package com.ravindra.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ravindra.model.CurrentWeather;

@Repository
public interface CurrentWeatherRepo extends CrudRepository<CurrentWeather, Long>{

}