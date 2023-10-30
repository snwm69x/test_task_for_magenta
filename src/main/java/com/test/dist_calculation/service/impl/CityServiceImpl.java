package com.test.dist_calculation.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dist_calculation.entity.City;
import com.test.dist_calculation.exception.EntityAlreadyExistsException;
import com.test.dist_calculation.exception.EntityNotExistsException;
import com.test.dist_calculation.repository.CityRepository;
import com.test.dist_calculation.service.CityService;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
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
    public void updateCity(City new_city) {
        Optional<City> old_city = cityRepository.findById(new_city.getId());
        if (!old_city.isPresent()) {
            cityRepository.save(new_city);
        }
        if (old_city.get().equals(new_city)) {
            return;
        } else {
            cityRepository.save(new_city);
        }
    }

    @Override
    public void deleteCity(City city) {
        Optional<City> old_city = cityRepository.findById(city.getId());
        if (!old_city.isPresent()) {
            throw new EntityNotExistsException("City with id " + city.getId() + " cannot be deleted because it does not exist");
        }
        cityRepository.delete(city);
    }

    @Override
    public void addCity(City city) {
        Optional<City> old_city = cityRepository.findByName(city.getName());
        if (!old_city.isPresent()) {
            cityRepository.save(city);
        } else {
            throw new EntityAlreadyExistsException("City with id " + city.getId() + " already exist");
        }
    }

}
