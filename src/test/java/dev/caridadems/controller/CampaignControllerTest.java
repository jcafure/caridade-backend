package dev.caridadems.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.caridadems.dto.CampaignDTO;
import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.service.CampaignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CampaignControllerTest {

    @MockitoBean
    private CampaignService campaignService;

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
        input.setName("Campanha do Mês de setembro");
        input.setDescription("Campanha para arrecadar alimentos e preparar almoços em comunidade.");
        input.setDateInit(init);
        input.setDateEnd(end);
        input.setStatus("OPEN");
        input.setMenuCampaignDTOS(List.of(buildMenuDto(18), buildMenuDto(22)));

        final var returned = new CampaignDTO();
        returned.setName(input.getName());
        returned.setDescription(input.getDescription());
        returned.setDateInit(input.getDateInit());
        returned.setDateEnd(input.getDateEnd());
        returned.setStatus(input.getStatus());
        returned.setMenuCampaignDTOS(List.of(buildMenuDto(18), buildMenuDto(22)));

        when(campaignService.newCampaing(any(CampaignDTO.class))).thenReturn(returned);

        mockMvc.perform(post("/campaigns/new-campaign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("Campanha do Mês de setembro")))
                .andExpect(jsonPath("$.status", equalTo("OPEN")))
                .andExpect(jsonPath("$.menuCampaignDTOS[0].id", equalTo(18)))
                .andExpect(jsonPath("$.menuCampaignDTOS[1].id", equalTo(22)));

        ArgumentCaptor<CampaignDTO> captor = ArgumentCaptor.forClass(CampaignDTO.class);
        verify(campaignService, times(1)).newCampaing(captor.capture());

        final var passed = captor.getValue();
        assert passed.getName().equals(input.getName());
        assert passed.getMenuCampaignDTOS().size() == 2;

    }

    private static MenuCampaignDTO buildMenuDto(Integer id) {
        final var dto = new MenuCampaignDTO();
        dto.setId(id);
        return dto;
    }
}