package com.test.dist_calculation.service;

import com.test.dist_calculation.entity.City;

public interface CrowFlightDistanceCalculatorService {
    public static final double EARTH_RADIUS = 6371302;
    Double calculate(City fromCity, City toCity);
}
