package dev.caridadems.mapper;

import dev.caridadems.domain.StatusCampaign;
import dev.caridadems.dto.CampaignDTO;
import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.model.Campaign;
import dev.caridadems.model.MenuCampaign;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CampaignMapperTest {

    @Mock
    private MenuCampaignMapper menuCampaignMapper;

    @InjectMocks
    private CampaignMapper campaignMapper;

    private LocalDate init;
    private LocalDate end;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        init = LocalDate.of(2025, 9, 15);
        end  = LocalDate.of(2025, 9, 28);
    }

    @Test
    void dtoToEntity() {
        final var dto = new CampaignDTO();
        dto.setName("Campanha Almoço de Setembro");
        dto.setDescription("Campanha para arrecadar alimentos");
        dto.setDateInit(init);
        dto.setDateEnd(end);
        dto.setStatus("Aberta");

        var entity = campaignMapper.dtoToEntity(dto);

        Assertions.assertThat(entity).isNotNull();
        assertThat(entity.getName()).isEqualTo(dto.getName());
        assertThat(entity.getDescription()).isEqualTo(dto.getDescription());
        assertThat(entity.getDateInit()).isEqualTo(init);
        assertThat(entity.getDateEnd()).isEqualTo(end);
        assertThat(entity.getStatus()).isEqualTo(StatusCampaign.OPEN);

    }

    @Test
    void entityToDto() {
        final var entity = new Campaign();
        entity.setName("Campanha Almoço de Setembro");
        entity.setDescription("Campanha para arrecadar alimentos");
        entity.setDateInit(init);
        entity.setDateEnd(end);
        entity.setStatus(StatusCampaign.OPEN);

        final var menu1 = new MenuCampaign();
        MenuCampaign menu2 = new MenuCampaign();
        entity.setMenuCampaigns(List.of(menu1, menu2));

        final var menuDto1 = new MenuCampaignDTO();
        menuDto1.setName("Carreteiro");
        MenuCampaignDTO menuDto2 = new MenuCampaignDTO();
        menuDto2.setName("Salada");

        when(menuCampaignMapper.entityToDto(menu1)).thenReturn(menuDto1);
        when(menuCampaignMapper.entityToDto(menu2)).thenReturn(menuDto2);

        final var dto = campaignMapper.entityToDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getDescription()).isEqualTo(entity.getDescription());
        assertThat(dto.getDateInit()).isEqualTo(init);
        assertThat(dto.getDateEnd()).isEqualTo(end);
        assertThat(dto.getStatus()).isEqualTo(StatusCampaign.OPEN.getDescription());

        assertThat(dto.getMenuCampaignDTOS()).isNotNull();
        assertThat(dto.getMenuCampaignDTOS().get(0).getName()).isEqualTo("Carreteiro");
        assertThat(dto.getMenuCampaignDTOS().get(1).getName()).isEqualTo("Salada");
    }
}