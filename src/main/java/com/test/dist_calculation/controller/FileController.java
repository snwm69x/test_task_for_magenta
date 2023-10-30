package com.test.dist_calculation.controller;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test.dist_calculation.dto.DistanceDTO;
import com.test.dist_calculation.entity.City;
import com.test.dist_calculation.entity.Distance;
import com.test.dist_calculation.service.CityService;
import com.test.dist_calculation.service.DistanceService;
import com.test.dist_calculation.service.CrowFlightDistanceCalculatorService;
import com.test.dist_calculation.util.CityAndDistanceWrapper;

@Controller
public class FileController {

    @Autowired
    private final CityService cityService;

    @Autowired
    private final DistanceService distanceService;

    @Autowired
    private final CrowFlightDistanceCalculatorService crowFlightDistanceCalculatorService;

    public FileController(CityService cityService, DistanceService distanceService, CrowFlightDistanceCalculatorService crowFlightDistanceCalculatorService) {
        this.distanceService = distanceService;
        this.crowFlightDistanceCalculatorService = crowFlightDistanceCalculatorService;
        this.cityService = cityService;
    }

    @GetMapping("/file")
    public String getFileView() {
        return "file";
    }

    @PostMapping("/file/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
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
