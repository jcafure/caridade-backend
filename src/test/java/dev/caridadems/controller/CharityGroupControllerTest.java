package dev.caridadems.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.caridadems.dto.AddressDTO;
import dev.caridadems.dto.CharityGroupDTO;
import dev.caridadems.dto.CityDTO;
import dev.caridadems.service.CharityGroupService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(CharityGroupController.class)
@AllArgsConstructor
class CharityGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CharityGroupService charityGroupService;

    private final ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateCharityGroup() throws Exception {
        final var requestDto = getCharityGroupDTO();

        when(charityGroupService.createCharityGroup(any(CharityGroupDTO.class)))
                .thenReturn(requestDto);

        mockMvc.perform(post("/groups/new-group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Grupo Esperança"))
                .andExpect(jsonPath("$.email").value("grupo@esperanca.org"))
                .andExpect(jsonPath("$.address[0].cep").value("79000-000"));

        verify(charityGroupService, times(1)).createCharityGroup(any(CharityGroupDTO.class));
    }

    private static CharityGroupDTO getCharityGroupDTO() {
        final var requestDto = new CharityGroupDTO();
        requestDto.setName("Grupo Esperança");
        requestDto.setEmail("grupo@esperanca.org");
        requestDto.setPhone("67999999999");
        requestDto.setDescription("Ajuda social");

        final var cityDTO  = new CityDTO();
        cityDTO.setName("Campo Grande");
        cityDTO.setState("MS");

        final var addressDTO = new AddressDTO();
        addressDTO.setCep("79000-000");
        addressDTO.setStreet("Rua das Acácias");
        addressDTO.setNumber("123");
        addressDTO.setComplement("Casa 1");
        addressDTO.setTypeAddress("Residência");
        addressDTO.setCity(cityDTO);

        requestDto.setAddress(List.of(addressDTO));
        return requestDto;
    }
}