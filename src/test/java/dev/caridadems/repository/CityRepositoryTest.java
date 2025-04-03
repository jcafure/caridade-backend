package dev.caridadems.repository;

import dev.caridadems.model.City;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CityRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CityRepository cityRepository;

    @AfterEach
    void tearDown() {
        cityRepository.deleteAll();
    }

    @Test
    void shouldFindCityByName() {
        final var city = new City();
        city.setName("Cidade teste");
        testEntityManager.persist(city);

        Optional<City> found = cityRepository.findCityByName("Cidade teste");

        assertTrue(found.isPresent());
        assertEquals("Cidade teste", found.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenCityNotFound() {
        Optional<City> found = cityRepository.findCityByName("Cidade Inexistente");
        assertTrue(found.isEmpty());
    }
}