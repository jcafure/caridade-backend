package dev.caridadems.mapper;

import dev.caridadems.dto.AddressDTO;
import dev.caridadems.dto.CharityGroupDTO;
import dev.caridadems.model.Address;
import dev.caridadems.model.CharityGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

class CharityGroupMapperTest {

    @InjectMocks
    private CharityGroupMapper charityGroupMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConverterDtoToEntity() {
        final var dto = new CharityGroupDTO();
        dto.setName("Grupo Esperança");
        dto.setEmail("esperanca@caridade.org");
        dto.setPhone("67999999999");
        dto.setDescription("Atendimento em comunidades carentes");

        final var entity = charityGroupMapper.converterDtoToEntity(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getPhone(), entity.getPhone());
        assertEquals(dto.getDescription(), entity.getDescription());
    }

    @Test
    void testToDTO() {
        final var entity = new CharityGroup();
        entity.setId(1);
        entity.setName("Grupo Esperança");
        entity.setEmail("esperanca@caridade.org");
        entity.setPhone("67999999999");
        entity.setDescription("Distribuição de alimentos");

        final var address = new Address();
        address.setId(100);
        entity.setAddress(List.of(address));

        final var mockAddressDTO = new AddressDTO();
        mockAddressDTO.setCep("79000-000");

        try (MockedStatic<AddressMapper> mocked = mockStatic(AddressMapper.class)) {
            mocked.when(() -> AddressMapper.toDTO(address)).thenReturn(mockAddressDTO);

            CharityGroupDTO dto = charityGroupMapper.toDTO(entity);

            assertEquals(entity.getId(), dto.getId());
            assertEquals(entity.getName(), dto.getName());
            assertEquals(entity.getEmail(), dto.getEmail());
            assertEquals(entity.getPhone(), dto.getPhone());
            assertEquals(entity.getDescription(), dto.getDescription());
            assertEquals(1, dto.getAddress().size());
            assertEquals("79000-000", dto.getAddress().getFirst().getCep());

            mocked.verify(() -> AddressMapper.toDTO(address), times(1));
        }
    }
}