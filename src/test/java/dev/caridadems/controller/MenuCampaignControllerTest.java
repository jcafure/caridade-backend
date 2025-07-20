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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        when(menuCampaignService.findAll(Mockito.isNull(), any(Pageable.class)))
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

        when(menuCampaignService.createMenuCampaign(any()))
                .thenReturn(responseDto);

        mockMvc.perform(post("/donation-menus/new-menu-campaign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Feijoada"));
    }

    @Test
    void shouldUpdateMenuCampaignSuccessfully() throws Exception {
        final var inputDto = new MenuCampaignDTO();
        inputDto.setId(6);
        inputDto.setName("Feijoada Solidária");

        when(menuCampaignService.updateMenu(any(MenuCampaignDTO.class))).thenReturn(inputDto);
        mockMvc.perform(put("/donation-menus/update-menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value("Feijoada Solidária"));

        verify(menuCampaignService).updateMenu(any(MenuCampaignDTO.class));
    }

    @Test
    void testDeleteMenuSuccessfully() throws Exception {
        final var menuId = 1;
        mockMvc.perform(delete("/donation-menus/delete-menu/{id}", menuId))
                .andExpect(status().isNoContent());

        verify(menuCampaignService).delete(menuId);
    }

    @Test
    void shouldReturnMenuCampaignDTOWhenIdExists() throws Exception {
        final var id = 1;
        final var dto = new MenuCampaignDTO();
        dto.setId(id);
        dto.setName("carreteiro");

        when(menuCampaignService.findById(id)).thenReturn(dto);

        mockMvc.perform(get("/donation-menus/find-by-id/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("carreteiro"));

        verify(menuCampaignService).findById(id);
    }


}