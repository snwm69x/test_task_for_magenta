package com.test.distCalculation.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.distCalculation.entity.Distance;
import com.test.distCalculation.service.CalculateDistanceControllerService;

@RestController
public class CalculateDistanceController {

    private final CalculateDistanceControllerService calculateDistanceControllerService;

    public CalculateDistanceController(CalculateDistanceControllerService calculateDistanceControllerService) {
        this.calculateDistanceControllerService = calculateDistanceControllerService;
    }

    @GetMapping("/calculateDistance")
    @ResponseBody
    public List<Distance> calculateDistance(@RequestParam String type, @RequestParam String cityFrom, @RequestParam String cityTo) {
        return calculateDistanceControllerService.handleCalculateDistanceRequest(type, cityFrom, cityTo);
    }
}
