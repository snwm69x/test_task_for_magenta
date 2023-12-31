package com.test.distCalculation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.distCalculation.entity.City;
import com.test.distCalculation.entity.Distance;

@Repository
public interface DistanceRepository extends JpaRepository<Distance, Long> {
    Optional<Distance> findByFromCityAndToCity(City fromCity, City toCity);
}
