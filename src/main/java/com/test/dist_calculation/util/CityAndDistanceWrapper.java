package com.test.dist_calculation.util;

import java.util.List;

import com.test.dist_calculation.dto.DistanceDTO;
import com.test.dist_calculation.entity.City;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityAndDistanceWrapper {
    private List<City> cities;
    private List<DistanceDTO> distances;
}
