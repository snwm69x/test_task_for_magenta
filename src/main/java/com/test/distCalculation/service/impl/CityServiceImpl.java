package com.test.distCalculation.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.test.distCalculation.entity.City;
import com.test.distCalculation.exception.EntityAlreadyExistsException;
import com.test.distCalculation.exception.EntityNotExistsException;
import com.test.distCalculation.repository.CityRepository;
import com.test.distCalculation.service.CityService;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public City getCityById(Long id) {
        Optional<City> city = cityRepository.findById(id);
        if (!city.isPresent()) {
            throw new EntityNotExistsException("City with id " + id + " not found");
        }
        return city.get();
    }

    @Override
    public City getCityByName(String name) {
        Optional<City> city = cityRepository.findByName(name);
        if (!city.isPresent()) {
            throw new EntityNotExistsException("City with name " + name + " not found");
        }
        return city.get();
    }

    @Override
    public void updateCity(City newCity) {
        Optional<City> oldCity = cityRepository.findById(newCity.getId());
        if (!oldCity.isPresent()) {
            cityRepository.save(newCity);
        }
        if (oldCity.get().equals(newCity)) {
            System.out.println("City with id " + newCity.getId() + " already exist");
            return;
        } else {
            cityRepository.save(newCity);
        }
    }

    @Override
    public void deleteCity(City city) {
        Optional<City> oldCity = cityRepository.findById(city.getId());
        if (!oldCity.isPresent()) {
            throw new EntityNotExistsException("City with id " + city.getId() + " cannot be deleted because it does not exist");
        }
        cityRepository.delete(city);
    }

    @Override
    public void addCity(City city) {
        Optional<City> oldCity = cityRepository.findByName(city.getName());
        if (!oldCity.isPresent()) {
            cityRepository.save(city);
        } else {
            throw new EntityAlreadyExistsException("City with id " + city.getId() + " already exist");
        }
    }

}
