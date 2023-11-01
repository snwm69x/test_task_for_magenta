package com.test.distCalculation.service;

import com.test.distCalculation.entity.City;

public interface CrowFlightDistanceCalculatorService {
    public static final double EARTH_RADIUS = 6371302;
    Double calculate(City fromCity, City toCity);
}
