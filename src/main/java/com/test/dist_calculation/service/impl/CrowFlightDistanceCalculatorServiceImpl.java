package com.test.dist_calculation.service.impl;

import org.springframework.stereotype.Service;

import com.test.dist_calculation.entity.City;
import com.test.dist_calculation.service.CrowFlightDistanceCalculatorService;

@Service
public class CrowFlightDistanceCalculatorServiceImpl implements CrowFlightDistanceCalculatorService {

    private Double lattitudeDifference(Double latitude1, Double latitude2) {
        return Math.toRadians(latitude2 - latitude1);
    }

    private Double longitudeDifference(Double longitude1, Double longitude2) {
        return Math.toRadians(longitude2 - longitude1);
    }

    @Override
    public Double calculate(City fromCity, City toCity) {
        double latdiff = lattitudeDifference(fromCity.getLatitude(), toCity.getLatitude());
        double longdiff = longitudeDifference(fromCity.getLongitude(), toCity.getLongitude());
        double a = Math.sin(latdiff / 2) * Math.sin(latdiff / 2) + Math.cos(Math.toRadians(fromCity.getLatitude()))
                * Math.cos(Math.toRadians(toCity.getLatitude())) * Math.sin(longdiff / 2) * Math.sin(longdiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
    
}
