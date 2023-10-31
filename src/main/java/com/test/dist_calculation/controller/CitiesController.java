package com.test.dist_calculation.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.dist_calculation.dto.CityDTO;
import com.test.dist_calculation.entity.City;
import com.test.dist_calculation.service.CityService;

@RestController
public class CitiesController {

    private final CityService cityService;
    
    public CitiesController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/city/getAll")
    @ResponseBody
    public List<CityDTO> getAllCity() {
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
