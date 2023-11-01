package com.test.distCalculation.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.test.distCalculation.entity.City;
import com.test.distCalculation.entity.Distance;
import com.test.distCalculation.entity.enums.CalculationType;
import com.test.distCalculation.service.CalculateDistanceControllerService;
import com.test.distCalculation.service.CityService;
import com.test.distCalculation.service.CrowFlightDistanceCalculatorService;
import com.test.distCalculation.service.DistanceService;
import com.test.distCalculation.util.DistancePair;

@Service
public class CalculateDistanceControllerServiceImpl implements CalculateDistanceControllerService {

    private final DistanceService distanceService;
    private final CrowFlightDistanceCalculatorService crowFlightDistanceCalculatorService;
    private final CityService cityService;

    public CalculateDistanceControllerServiceImpl(DistanceService distanceService,
            CrowFlightDistanceCalculatorService crowFlightDistanceCalculatorService, CityService cityService) {
        this.distanceService = distanceService;
        this.crowFlightDistanceCalculatorService = crowFlightDistanceCalculatorService;
        this.cityService = cityService;
    }

    @Override
    public List<Distance> handleCalculateDistanceRequest(String type, String cityFrom, String cityTo) {
        int counter = 0;
        CalculationType calculationType = CalculationType.valueOf(type);
        List<Distance> distances = new ArrayList<>();
        List<String> fromCities = Arrays.asList(cityFrom.split(","));
        List<String> toCities = Arrays.asList(cityTo.split(","));

        for (int i = 0; i < fromCities.size(); i++) {
            City fromCity = cityService.getCityByName(fromCities.get(i).trim());

            for (int j = 0; j < toCities.size(); j++) {
                City toCity = cityService.getCityByName(toCities.get(j).trim());

                switch (calculationType) {
                    case Crowflight:
                        Distance crowflight = Distance.builder()
                                .id((long) counter++)
                                .fromCity(fromCity)
                                .toCity(toCity)
                                .distance(crowFlightDistanceCalculatorService.calculate(fromCity, toCity))
                                .build();
                        distances.add(crowflight);
                        break;
                    case DistanceMatrix:
                        distances.add(distanceService.getDistanceByCities(fromCity, toCity));
                        break;
                    case All:
                        DistancePair distancePair = distanceService.getDistanceByCitiesInBothWays(fromCity, toCity);
                        distances.add(distancePair.getCrowflightDistance());
                        distances.add(distancePair.getFromDbDistance());
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Invalid type. Expected 'Crowflight','DistanceMatrix' or 'All'.");
                }
            }
        }
        return distances;
    }
}
