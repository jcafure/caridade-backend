package dev.caridadems.service;

import dev.caridadems.dto.CampaignDTO;
import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.mapper.CampaignMapper;
import dev.caridadems.model.Campaign;
import dev.caridadems.model.MenuCampaign;
import dev.caridadems.repository.CampaingRepository;
import dev.caridadems.repository.MenuCampaignRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CampaignServiceTest {

    @Mock
    private CampaignMapper campaignMapper;

    @Mock
    private CampaingRepository campaingRepository;

    @Mock
    private MenuCampaignRepository menuCampaignRepository;

    @InjectMocks
    private CampaignService campaignService;

    private LocalDate init;
    private LocalDate end;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        init = LocalDate.of(2025, 9, 15);
        end  = LocalDate.of(2025, 9, 28);
    }

    @Test
    public void testNewCampaing() {
        final var inputDto = new CampaignDTO();
        final var menu18 = mock(MenuCampaign.class);
        final var menu22 = mock(MenuCampaign.class);
        inputDto.setName("Campanha do Dia das Crianças");
        inputDto.setDescription("Campanha para arrecadar alimentos");
        inputDto.setDateInit(init);
        inputDto.setDateEnd(end);
        inputDto.setStatus("Aberta");
        inputDto.setMenuCampaignDTOS(List.of(
                buildMenuDto(18),
                buildMenuDto(22)
        ));

        final var expectedOutput = new CampaignDTO();
        expectedOutput.setName(inputDto.getName());
        expectedOutput.setDescription(inputDto.getDescription());
        expectedOutput.setDateInit(inputDto.getDateInit());
        expectedOutput.setDateEnd(inputDto.getDateEnd());
        expectedOutput.setStatus(inputDto.getStatus());

        final var mappedEntity = new Campaign();
        Campaign savedEntity = Mockito.spy(new Campaign());
        savedEntity.setId(1);

        when(campaignMapper.dtoToEntity(inputDto)).thenReturn(mappedEntity);
        when(campaingRepository.save(mappedEntity)).thenReturn(savedEntity);
        when(menuCampaignRepository.findAllById(List.of(18, 22)))
                .thenReturn(List.of(menu18, menu22));
        when(campaignMapper.entityToDto(savedEntity)).thenReturn(expectedOutput);

        final var response = campaignService.newCampaing(inputDto);

        assertThat(response).isNotNull();

        verify(campaignMapper).dtoToEntity(inputDto);
        verify(campaingRepository).save(mappedEntity);
        verify(menuCampaignRepository).findAllById(List.of(18, 22));
        verify(menu18).setCampaign(savedEntity);
        verify(menu22).setCampaign(savedEntity);

        ArgumentCaptor<List<MenuCampaign>> captor = ArgumentCaptor.forClass(List.class);
        verify(savedEntity).setMenuCampaigns(captor.capture());
        List<MenuCampaign> linkedMenus = captor.getValue();
        assertThat(linkedMenus).containsExactlyInAnyOrder(menu18, menu22);

        verify(campaignMapper).entityToDto(savedEntity);

        verifyNoMoreInteractions(campaignMapper, campaingRepository, menuCampaignRepository, menu18, menu22);

    }

    private static MenuCampaignDTO buildMenuDto(Integer id) {
        final var dto = new MenuCampaignDTO();
        dto.setId(id);
        return dto;
    }
}