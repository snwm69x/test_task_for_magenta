package com.test.dist_calculation.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dist_calculation.entity.City;
import com.test.dist_calculation.entity.Distance;
import com.test.dist_calculation.exception.EntityAlreadyExistsException;
import com.test.dist_calculation.repository.DistanceRepository;
import com.test.dist_calculation.service.CrowFlightDistanceCalculatorService;
import com.test.dist_calculation.service.DistanceService;

@Service
public class DistanceServiceImpl implements DistanceService {

    @Autowired
    private final DistanceRepository distanceRepository;

    @Autowired
    private final CrowFlightDistanceCalculatorService crowFlightDistanceCalculatorService;

    public DistanceServiceImpl(DistanceRepository distanceRepository, CrowFlightDistanceCalculatorService crowFlightDistanceCalculatorService) {
        this.distanceRepository = distanceRepository;
        this.crowFlightDistanceCalculatorService = crowFlightDistanceCalculatorService;
    }

    @Override
    public Distance getDistanceByCities(City fromCity, City toCity) {
        Optional<Distance> exist_dist = distanceRepository.findByFromCityAndToCity(fromCity, toCity);
        if (exist_dist.isPresent()) {
            return exist_dist.get();
        }
        Distance new_dist = Distance.builder()
                .fromCity(fromCity)
                .toCity(toCity)
                .distance(crowFlightDistanceCalculatorService.calculate(fromCity,toCity))
                .build();
        distanceRepository.save(new_dist);
        return new_dist;
    }

    @Override
    public void addDistance(Distance distance) {
        Optional<Distance> check_if_exist = distanceRepository.findByFromCityAndToCity(distance.getFromCity(), distance.getToCity());
        if(!check_if_exist.isPresent()) {
            distanceRepository.save(distance);
        } else {
            throw new EntityAlreadyExistsException("Distance between cities " + distance.getFromCity().getName() + " and " + distance.getToCity().getName() + " already exist");
        }
    }
    
}
