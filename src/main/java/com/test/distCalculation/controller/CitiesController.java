package com.test.distCalculation.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.distCalculation.dto.CityDTO;
import com.test.distCalculation.service.CitiesControllerService;

@RestController
public class CitiesController {

    private final CitiesControllerService citiesControllerService;

    public CitiesController(CitiesControllerService citiesControllerService) {
        this.citiesControllerService = citiesControllerService;
    }

    @GetMapping("/city/getAll")
    @ResponseBody
    public List<CityDTO> getAllCity() {
        return citiesControllerService.handleGetAllCitiesRequest();
    }
}
