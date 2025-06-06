package dev.caridadems.repository;

import dev.caridadems.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    Optional<City> findCityByNameContainingIgnoreCase(String name);
}
