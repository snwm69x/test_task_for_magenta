package com.test.dist_calculation.service;

import com.test.dist_calculation.entity.City;
import com.test.dist_calculation.entity.Distance;

public interface DistanceService {
    Distance getDistanceByCities(City fromCity, City toCity);
    void addDistance(Distance distance);
}
