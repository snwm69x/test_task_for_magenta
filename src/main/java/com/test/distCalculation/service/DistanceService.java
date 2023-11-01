package com.test.distCalculation.service;

import com.test.distCalculation.entity.City;
import com.test.distCalculation.entity.Distance;
import com.test.distCalculation.util.DistancePair;

public interface DistanceService {
    Distance getDistanceByCities(City fromCity, City toCity);
    void addDistance(Distance distance);
    DistancePair getDistanceByCitiesInBothWays(City fromCity, City toCity);
}
