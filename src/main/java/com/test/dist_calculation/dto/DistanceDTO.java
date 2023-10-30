package com.test.dist_calculation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistanceDTO {
    private String fromCity;
    private String toCity;
    private Double distance;
}
