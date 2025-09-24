package dev.caridadems.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.caridadems.dto.CampaignDTO;
import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.service.CampaingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CampaignControllerTest {

    @MockitoBean
    private CampaingService campaingService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();
    private LocalDate init;
    private LocalDate end;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        init = LocalDate.of(2025, 9, 15);
        end  = LocalDate.of(2025, 9, 28);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void testCreateCampaign() throws Exception {
        final var input = new CampaignDTO();
        input.setName("Campanha do Dia das Crianças");
        input.setDescription("Campanha para arrecadar alimentos e preparar almoços em comunidade.");
        input.setDateInit(LocalDate.parse("2025-09-15"));
        input.setDateEnd(LocalDate.parse("2025-10-15"));
        input.setStatus("OPEN");
        input.setMenuCampaignDTOS(List.of(buildMenuDto(18), buildMenuDto(22)));

        final var returned = new CampaignDTO();
        returned.setName(input.getName());
        returned.setDescription(input.getDescription());
        returned.setDateInit(input.getDateInit());
        returned.setDateEnd(input.getDateEnd());
        returned.setStatus(input.getStatus());
        returned.setMenuCampaignDTOS(List.of(buildMenuDto(18), buildMenuDto(22)));

        when(campaingService.newCampaing(any(CampaignDTO.class))).thenReturn(returned);

        mockMvc.perform(post("/campaigns/new-campaign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("Campanha do Dia das Crianças")))
                .andExpect(jsonPath("$.status", equalTo("OPEN")))
                .andExpect(jsonPath("$.menuCampaignDTOS[0].id", equalTo(18)))
                .andExpect(jsonPath("$.menuCampaignDTOS[1].id", equalTo(22)));


    }

    private static MenuCampaignDTO buildMenuDto(Integer id) {
        final var dto = new MenuCampaignDTO();
        dto.setId(id);
        return dto;
    }
}