package dev.caridadems.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.caridadems.dto.DonorRegisterDTO;
import dev.caridadems.dto.DonorRegisterResponseDTO;
import dev.caridadems.service.DonorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DonorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DonorService donorService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShouldReturnCreatedDonor() throws Exception {
        final var dto = new DonorRegisterDTO();
        dto.setName("Jaime");
        dto.setEmail("jaime@caridade.com");
        dto.setPhone("(67) 99999-9999");
        dto.setExternalId(123);

        final var responseDto = new DonorRegisterResponseDTO(true, 1, "Jaime");

        Mockito.when(donorService.newDonor(dto)).thenReturn(responseDto);
        mockMvc.perform(post("/donors/new-donor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jaime"));
    }
}