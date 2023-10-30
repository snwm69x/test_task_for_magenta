package com.test.dist_calculation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.dist_calculation.entity.City;
import com.test.dist_calculation.entity.Distance;
import com.test.dist_calculation.entity.enums.CalculationType;
import com.test.dist_calculation.service.CityService;
import com.test.dist_calculation.service.CrowFlightDistanceCalculatorService;
import com.test.dist_calculation.service.DistanceService;

@RestController
public class CalculateDistanceController {

    private final DistanceService distanceService;
    private final CrowFlightDistanceCalculatorService crowFlightDistanceCalculatorService;
    private final CityService cityService;

    public CalculateDistanceController(DistanceService distanceService, CrowFlightDistanceCalculatorService crowFlightDistanceCalculatorService, CityService cityService) {
        this.distanceService = distanceService;
        this.crowFlightDistanceCalculatorService = crowFlightDistanceCalculatorService;
        this.cityService = cityService;
    }

    @GetMapping("/calculateDistance")
    @ResponseBody
    public List<Distance> calculateDistance(@RequestParam String type, @RequestParam String cityFrom, @RequestParam String cityTo) {

        CalculationType calculationType = CalculationType.valueOf(type);
        List<Distance> distances = new ArrayList<>();
        List<String> fromCities = Arrays.asList(cityFrom.split(","));
        List<String> toCities = Arrays.asList(cityTo.split(","));

        if (fromCities.size() != toCities.size()) {
            throw new IllegalArgumentException("The number of cities in 'cityFrom' and 'cityTo' must be the same.");
        }

        for (int i = 0; i < fromCities.size(); i++) {
            City fromCity = cityService.getCityByName(fromCities.get(i).trim());
            City toCity = cityService.getCityByName(toCities.get(i).trim());
    
            switch (calculationType) {
                case Crowflight:
                    Distance crowflight = Distance.builder()
                                        .id((long) i)
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
                    distances.add(distanceService.getDistanceByCities(fromCity, toCity));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid type. Expected 'Crowflight','DistanceMatrix' or 'All'.");
            }
        }

        return distances;
    }
}
