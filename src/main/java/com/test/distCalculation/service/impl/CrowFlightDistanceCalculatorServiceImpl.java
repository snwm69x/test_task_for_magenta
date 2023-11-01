package com.test.distCalculation.service.impl;

import java.text.DecimalFormat;

import org.springframework.stereotype.Service;

import com.test.distCalculation.entity.City;
import com.test.distCalculation.exception.DistanceCalculationException;
import com.test.distCalculation.service.CrowFlightDistanceCalculatorService;

@Service
public class CrowFlightDistanceCalculatorServiceImpl implements CrowFlightDistanceCalculatorService {

    private Double lattitudeDifference(Double latitude1, Double latitude2) {
        return Math.toRadians(latitude2 - latitude1);
    }

    private Double longitudeDifference(Double longitude1, Double longitude2) {
        return Math.toRadians(longitude2 - longitude1);
    }

    // возвращает расстояние в метрах
    @Override
    public Double calculate(City fromCity, City toCity) {
        if (fromCity == null || toCity == null) {
            throw new DistanceCalculationException("Invalid input: fromCity and toCity cannot be null");
        }
        double latdiff = lattitudeDifference(fromCity.getLatitude(), toCity.getLatitude());
        double longdiff = longitudeDifference(fromCity.getLongitude(), toCity.getLongitude());
        double a = Math.sin(latdiff / 2) * Math.sin(latdiff / 2) + Math.cos(Math.toRadians(fromCity.getLatitude()))
                * Math.cos(Math.toRadians(toCity.getLatitude())) * Math.sin(longdiff / 2) * Math.sin(longdiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;
        if (Double.isNaN(distance)) {
            throw new DistanceCalculationException("Distance cannot be calculated");
        }
        DecimalFormat df = new DecimalFormat("#.##");
        distance = Double.valueOf(df.format(distance));
        return distance;
    }

}