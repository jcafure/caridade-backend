package dev.caridadems.mapper;

import dev.caridadems.domain.TypeAddress;
import dev.caridadems.dto.AddressDTO;
import dev.caridadems.model.Address;
import dev.caridadems.model.City;
import dev.caridadems.model.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class AddressMapperTest {

    @InjectMocks
    private AddressMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvertDtoToEntity() {
        final var city = new City();
        final var state = new State();
        state.setName("Mato Grosso do Sul");
        city.setName("Campo Grande");
        city.setState(state);

        final var dto = new AddressDTO();
        dto.setCep("79000-000");
        dto.setStreet("Rua das Acácias");
        dto.setNumber("123");
        dto.setComplement("Casa 2");
        dto.setTypeAddress("Residência");

        final var entity = mapper.convertDtoToEntity(dto, city);

        Assertions.assertEquals(dto.getCep(), entity.getCep());
        Assertions.assertEquals(dto.getStreet(), entity.getStreet());
        Assertions.assertEquals(dto.getNumber(), entity.getNumber());
        Assertions.assertEquals(dto.getComplement(), entity.getComplement());
        Assertions.assertEquals(city, entity.getCity());
        Assertions.assertEquals(TypeAddress.HOME, entity.getTypeAddress());
    }

    @Test
    void testToDTO() {
        final var state = new State();
        state.setName("Mato Grosso do Sul");

        final var city = new City();
        city.setName("Campo Grande");
        city.setState(state);

        final var entity = new Address();
        entity.setId(10);
        entity.setCep("79001-100");
        entity.setStreet("Av. Brizas");
        entity.setNumber("100");
        entity.setComplement("Apartamento");
        entity.setTypeAddress(TypeAddress.HOME);
        entity.setCity(city);

        final var dto = AddressMapper.toDTO(entity);

        Assertions.assertEquals(entity.getId(), dto.getId());
        Assertions.assertEquals(entity.getCep(), dto.getCep());
        Assertions.assertEquals(entity.getStreet(), dto.getStreet());
        Assertions.assertEquals(entity.getNumber(), dto.getNumber());
        Assertions.assertEquals(entity.getComplement(), dto.getComplement());
        Assertions.assertEquals("Residência", dto.getTypeAddress());
        Assertions.assertNotNull(dto.getCity());
        Assertions.assertEquals("Campo Grande", dto.getCity().getName());
        Assertions.assertEquals("Mato Grosso do Sul", dto.getCity().getState());
    }
}