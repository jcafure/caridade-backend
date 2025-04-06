package dev.caridadems.service;

import dev.caridadems.model.City;
import dev.caridadems.model.State;
import dev.caridadems.repository.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class CityServiceTest {

    @InjectMocks
    private CityService cityService;

    @Mock
    private CityRepository cityRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findCityByName() {
        final var nameCity = "Campo Grande";
        final var state = new State();
        final var city = new City();

        state.setId(1);
        state.setName("Mato Grosso do Sul");
        state.setSigla("MS");

        city.setState(state);
        city.setName(nameCity);

        Mockito.when(cityRepository.findCityByName(nameCity)).thenReturn(Optional.of(city));

        final var response = cityService.findCityByName(nameCity);
        assertNotNull(response);
        Assertions.assertEquals(nameCity, response.getName());
        Assertions.assertEquals("Mato Grosso do Sul", response.getState().getName());
        Assertions.assertEquals("MS", state.getSigla());


    }
}