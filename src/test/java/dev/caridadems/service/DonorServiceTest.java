package dev.caridadems.service;

import dev.caridadems.dto.AddressDTO;
import dev.caridadems.dto.CityDTO;
import dev.caridadems.dto.DonorRegisterDto;
import dev.caridadems.mapper.AddressMapper;
import dev.caridadems.mapper.DonorMapper;
import dev.caridadems.model.Address;
import dev.caridadems.model.City;
import dev.caridadems.model.Donor;
import dev.caridadems.repository.DonorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DonorServiceTest {

    @InjectMocks
    private DonorService donorService;
    @Mock
    private AddressMapper addressMapper;
    @Mock
    private CityService cityService;
    @Mock
    private DonorMapper donorMapper;
    @Mock
    private DonorRepository donorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void newDonor() {
        var dto = new DonorRegisterDto();
        dto.setName("Jaime");
        dto.setEmail("jaime@exemplo.com");
        dto.setPhone("67999999999");
        dto.setExternalId(123);

        CityDTO cityDTO = new CityDTO();
        cityDTO.setName("Campo Grande");

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCep("79000000");
        addressDTO.setStreet("Rua Exemplo");
        addressDTO.setNumber("100");
        addressDTO.setCity(cityDTO);
        addressDTO.setTypeAddress("HOME");
        dto.setAddressDTOS(List.of(addressDTO));

        City city = new City();
        city.setName("Campo Grande");

        Donor donor = new Donor();
        donor.setName("Jaime");
        donor.setEmail("jaime@exemplo.com");

        Address address = new Address();
        address.setCity(city);

        Donor donorSaved = new Donor();
        donorSaved.setId(1);
        donorSaved.setName("Jaime");

        when(donorMapper.dtoToEntity(dto)).thenReturn(donor);
        when(cityService.findCityByName("Campo Grande")).thenReturn(city);
        when(addressMapper.convertDtoToEntity(addressDTO, city)).thenReturn(address);
        when(donorRepository.save(donor)).thenReturn(donorSaved);

        var response = donorService.newDonor(dto);

        assertTrue(response.isSuccess());
        assertEquals(1, response.getId());
        assertEquals("Jaime", response.getName());

        verify(donorRepository).save(donor);
    }
}