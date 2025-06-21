package dev.caridadems.mapper;

import dev.caridadems.dto.DonorRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DonorMapperTest {

    @InjectMocks
    private DonorMapper donorMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void dtoToEntity() {
        final var dto = new DonorRegisterDTO();
        dto.setEmail("admin@caridade.com");
        dto.setName("admin");
        dto.setPhone("(67) 99999-9999");
        dto.setExternalId(123);

        final var donor = donorMapper.dtoToEntity(dto);

        assertNotNull(donor);
        assertEquals(dto.getEmail(), donor.getEmail());
        assertEquals(dto.getName(), donor.getName());
        assertEquals(dto.getPhone(), donor.getPhone());
        assertEquals(dto.getExternalId(), donor.getExternalId());
    }
}