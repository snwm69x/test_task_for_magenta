package com.test.distCalculation.service.impl;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test.distCalculation.dto.DistanceDTO;
import com.test.distCalculation.entity.City;
import com.test.distCalculation.entity.Distance;
import com.test.distCalculation.service.CityService;
import com.test.distCalculation.service.CrowFlightDistanceCalculatorService;
import com.test.distCalculation.service.DistanceService;
import com.test.distCalculation.service.FileControllerService;
import com.test.distCalculation.util.CityAndDistanceWrapper;

@Service
public class FileControllerServiceImpl implements FileControllerService {

    private final CityService cityService;
    private final DistanceService distanceService;
    private final CrowFlightDistanceCalculatorService crowFlightDistanceCalculatorService;

    public FileControllerServiceImpl(CityService cityService, DistanceService distanceService, CrowFlightDistanceCalculatorService crowFlightDistanceCalculatorService) {
        this.cityService = cityService;
        this.distanceService = distanceService;
        this.crowFlightDistanceCalculatorService = crowFlightDistanceCalculatorService;
    }

    @Override
    public String handleFileUpload(MultipartFile file) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            InputStream fileStream = file.getInputStream();
            CityAndDistanceWrapper cityAndDistanceWrapper = xmlMapper.readValue(fileStream, CityAndDistanceWrapper.class);
            for (City c : cityAndDistanceWrapper.getCities()) {
                cityService.addCity(c);
            }
            for (DistanceDTO d : cityAndDistanceWrapper.getDistances()) {
                Distance distance = new Distance();
                distance.setFromCity(cityService.getCityByName(d.getFromCity()));
                distance.setToCity(cityService.getCityByName(d.getToCity()));
                if(d.getDistance() == null) {
                    distance.setDistance(crowFlightDistanceCalculatorService.calculate(distance.getFromCity(),distance.getToCity()));
                }
                System.out.println(distance.toString());
                distanceService.addDistance(distance);
            }
            
        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
            return "Error processing file: " + e.getMessage();
        }
        return "File uploaded successfully!";
    }
    
}
