package dev.caridadems.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.caridadems.dto.AddressDTO;
import dev.caridadems.dto.CharityGroupDTO;
import dev.caridadems.service.CharityGroupService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CharityGroupController.class)
class CharityGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CharityGroupService charityGroupService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CharityGroupService charityGroupService() {
            return mock(CharityGroupService.class);
        }
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

    private CharityGroupDTO getCharityGroupDTO() {
        final var dto = new CharityGroupDTO();
        dto.setName("Grupo Esperança");
        dto.setEmail("grupo@esperanca.org");

        final var address = new AddressDTO();
        address.setCep("79000-000");
        dto.setAddress(List.of(address));

        return dto;
    }
}
