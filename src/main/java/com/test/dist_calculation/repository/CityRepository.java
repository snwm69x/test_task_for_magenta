package com.test.dist_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.dist_calculation.entity.City;
import java.util.Optional;


@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String name);
    Optional<City> findById(Long id);
}
