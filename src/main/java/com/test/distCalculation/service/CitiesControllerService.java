package com.test.distCalculation.service;

import java.util.List;

import com.test.distCalculation.dto.CityDTO;

public interface CitiesControllerService {
    List<CityDTO> handleGetAllCitiesRequest();
}
