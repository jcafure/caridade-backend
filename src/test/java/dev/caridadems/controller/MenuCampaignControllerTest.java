package dev.caridadems.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.service.MenuCampaignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MenuCampaignControllerTest {

    @MockitoBean
    private MenuCampaignService menuCampaignService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMenus() throws Exception {
        final var dto = new MenuCampaignDTO();
        dto.setName("Feijoada");

        Page<MenuCampaignDTO> page = new PageImpl<>(List.of(dto));

        Mockito.when(menuCampaignService.findAll(Mockito.isNull(), Mockito.any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/donation-menus/all")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Feijoada"));
    }

    @Test
    void createNewMenuCampaign_shouldReturnCreatedMenu() throws Exception {
        final var requestDto = new MenuCampaignDTO();
        final var responseDto = new MenuCampaignDTO();

        responseDto.setName("Feijoada");
        requestDto.setName("Feijoada");

        Mockito.when(menuCampaignService.createMenuCampaign(Mockito.any()))
                .thenReturn(responseDto);

        mockMvc.perform(post("/donation-menus/new-menu-campaign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Feijoada"));
    }
}