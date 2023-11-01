package com.test.distCalculation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.test.distCalculation.dto.CityDTO;
import com.test.distCalculation.entity.City;
import com.test.distCalculation.service.CitiesControllerService;
import com.test.distCalculation.service.CityService;

@Service
public class CitiesControllerServiceImpl implements CitiesControllerService {

    private final CityService cityService;

    public CitiesControllerServiceImpl(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public List<CityDTO> handleGetAllCitiesRequest() {
        List<CityDTO> cityDTOList = new ArrayList<>();
        List<City> list = cityService.getAllCities();
        for(City c : list) {
            cityDTOList.add(CityDTO.builder()
                    .id(c.getId())
                    .name(c.getName())
                    .build());
        }
        return cityDTOList;
    }
    
}
