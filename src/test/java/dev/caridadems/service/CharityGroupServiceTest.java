package dev.caridadems.service;

import dev.caridadems.dto.AddressDTO;
import dev.caridadems.dto.CharityGroupDTO;
import dev.caridadems.dto.CityDTO;
import dev.caridadems.mapper.AddressMapper;
import dev.caridadems.mapper.CharityGroupMapper;
import dev.caridadems.model.Address;
import dev.caridadems.model.CharityGroup;
import dev.caridadems.model.City;
import dev.caridadems.model.State;
import dev.caridadems.repository.CharityGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharityGroupServiceTest {

    @InjectMocks
    private CharityGroupService charityGroupService;

    @Mock
    private CharityGroupMapper charityGroupMapper;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private CharityGroupRepository charityGroupRepository;

    @Mock
    private CityService cityService;

    private CharityGroupDTO dto;
    private CharityGroup groupEntity;
    private AddressDTO addressDTO;
    private Address addressEntity;
    private City city;

    @BeforeEach
    void setUp() {
        dto = new CharityGroupDTO();
        dto.setName("Grupo Luz");
        dto.setEmail("grupo@luz.org");
        dto.setPhone("67999999999");
        dto.setDescription("Distribuição de alimentos");

        final var cityDTO = new CityDTO();
        cityDTO.setName("Campo Grande");
        cityDTO.setState("MS");

        addressDTO = new AddressDTO();
        addressDTO.setCep("79000-000");
        addressDTO.setStreet("Rua A");
        addressDTO.setNumber("123");
        addressDTO.setComplement("Casa 1");
        addressDTO.setTypeAddress("Residência");
        addressDTO.setCity(cityDTO);
        dto.setAddress(List.of(addressDTO));

        State state = new State();
        state.setName("Mato Grosso do Sul");
        state.setSigla("MS");

        city = new City();
        city.setName("Campo Grande");
        city.setState(state);

        groupEntity = new CharityGroup();
        groupEntity.setName(dto.getName());
        groupEntity.setEmail(dto.getEmail());
        groupEntity.setPhone(dto.getPhone());
        groupEntity.setDescription(dto.getDescription());

        addressEntity = new Address();
        addressEntity.setCep(addressDTO.getCep());
        addressEntity.setStreet(addressDTO.getStreet());
        addressEntity.setNumber(addressDTO.getNumber());
        addressEntity.setComplement(addressDTO.getComplement());
        addressEntity.setCity(city);
    }

    @Test
    void shouldCreateCharityGroupWithAddresses() {
        when(charityGroupMapper.converterDtoToEntity(dto)).thenReturn(groupEntity);
        when(cityService.findCityByName("Campo Grande")).thenReturn(city);
        when(addressMapper.convertDtoToEntity(addressDTO, city)).thenReturn(addressEntity);
        when(charityGroupRepository.save(groupEntity)).thenReturn(groupEntity);
        when(charityGroupMapper.toDTO(groupEntity)).thenReturn(dto);

        CharityGroupDTO result = charityGroupService.createCharityGroup(dto);

        assertNotNull(result);
        assertEquals("Grupo Luz", result.getName());
        verify(cityService, times(1)).findCityByName("Campo Grande");
        verify(addressMapper, times(1)).convertDtoToEntity(addressDTO, city);
        verify(charityGroupRepository, times(1)).save(groupEntity);
        verify(charityGroupMapper, times(1)).toDTO(groupEntity);
    }
}