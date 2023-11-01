package com.test.distCalculation.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.test.distCalculation.entity.City;
import com.test.distCalculation.entity.Distance;
import com.test.distCalculation.exception.EntityAlreadyExistsException;
import com.test.distCalculation.repository.DistanceRepository;
import com.test.distCalculation.service.CrowFlightDistanceCalculatorService;
import com.test.distCalculation.service.DistanceService;
import com.test.distCalculation.util.DistancePair;

@Service
public class DistanceServiceImpl implements DistanceService {

    private final DistanceRepository distanceRepository;
    private final CrowFlightDistanceCalculatorService crowFlightDistanceCalculatorService;

    public DistanceServiceImpl(DistanceRepository distanceRepository,
            CrowFlightDistanceCalculatorService crowFlightDistanceCalculatorService) {
        this.distanceRepository = distanceRepository;
        this.crowFlightDistanceCalculatorService = crowFlightDistanceCalculatorService;
    }

    @Override
    public Distance getDistanceByCities(City fromCity, City toCity) {
        Optional<Distance> existDist = distanceRepository.findByFromCityAndToCity(fromCity, toCity);
        if (existDist.isPresent()) {
            return existDist.get();
        }
        Distance newDist = Distance.builder()
                .fromCity(fromCity)
                .toCity(toCity)
                .distance(crowFlightDistanceCalculatorService.calculate(fromCity, toCity))
                .build();
        addDistance(newDist);
        return newDist;
    }

    @Override
    public void addDistance(Distance distance) {
        Optional<Distance> checkIfExists = distanceRepository.findByFromCityAndToCity(distance.getFromCity(),
                distance.getToCity());
        if (!checkIfExists.isPresent()) {
            distanceRepository.save(distance);
        } else {
            throw new EntityAlreadyExistsException("Distance between cities " + distance.getFromCity().getName()
                    + " and " + distance.getToCity().getName() + " already exist");
        }
    }

    @Override
    public DistancePair getDistanceByCitiesInBothWays(City fromCity, City toCity) {
        DistancePair distancePair = new DistancePair();
        Optional<Distance> existDist = distanceRepository.findByFromCityAndToCity(fromCity, toCity);
        if (existDist.isPresent()) {
            distancePair.setFromDbDistance(existDist.get());
        } else {
            Distance newDist = Distance.builder()
                    .fromCity(fromCity)
                    .toCity(toCity)
                    .distance(crowFlightDistanceCalculatorService.calculate(fromCity, toCity))
                    .build();
            addDistance(newDist);
            distancePair.setFromDbDistance(newDist);
        }
        distancePair.setCrowflightDistance(distancePair.getFromDbDistance());
        return distancePair;
    }

}
