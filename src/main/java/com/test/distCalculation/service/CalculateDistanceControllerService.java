package com.test.distCalculation.service;

import java.util.List;

import com.test.distCalculation.entity.Distance;

public interface CalculateDistanceControllerService {
    List<Distance> handleCalculateDistanceRequest(String type, String cityFrom, String cityTo);
}
