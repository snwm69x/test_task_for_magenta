package com.test.distCalculation.util;

import java.util.List;

import com.test.distCalculation.dto.DistanceDTO;
import com.test.distCalculation.entity.City;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityAndDistanceWrapper {
    private List<City> cities;
    private List<DistanceDTO> distances;
}
