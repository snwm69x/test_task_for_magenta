package com.test.dist_calculation.service;

import java.util.List;

import com.test.dist_calculation.entity.City;

public interface CityService {
    List<City> getAllCities();
    City getCityById(Long id);
    City getCityByName(String name);
    void updateCity(City city);
    void deleteCity(City city);
    void addCity(City city);
}