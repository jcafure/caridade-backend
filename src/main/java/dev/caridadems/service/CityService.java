package dev.caridadems.service;

import dev.caridadems.model.City;
import dev.caridadems.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CityService {

    private CityRepository cityRepository;

    public City findCityByName(String name) {
       return cityRepository.findCityByNameContainingIgnoreCase(name)
                .orElseThrow(() -> new IllegalArgumentException("Cidade não encontrada: " + name));
    }
}
