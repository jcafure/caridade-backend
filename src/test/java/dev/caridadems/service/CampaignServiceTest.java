package dev.caridadems.service;

import dev.caridadems.domain.StatusCampaign;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

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
    void testNewCampaing() {
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

    @Test
    void test_ShouldCampaignsAll() {
       final var campaign = new Campaign();
        campaign.setName("Campanha Teste all");
        campaign.setDescription("Jaimelson");
        campaign.setDateInit(LocalDate.now());
        campaign.setDateEnd(LocalDate.now().plusDays(10));
        campaign.setStatus(StatusCampaign.OPEN);

        final var campaignDTO = new CampaignDTO();
        campaignDTO.setName("Campanha Teste all");
        campaignDTO.setDescription("Jaimelson");
        campaignDTO.setDateInit(campaign.getDateInit());
        campaignDTO.setDateEnd(campaign.getDateEnd());
        campaignDTO.setStatus("Aberta");

        Pageable pageable = PageRequest.of(0, 5);

        Page<Campaign> page = new PageImpl<>(List.of(campaign), pageable, 1);
        when(campaingRepository.findAllByStatus( StatusCampaign.OPEN, pageable)).thenReturn(page);
        when(campaignMapper.entityToDto(campaign)).thenReturn(campaignDTO);

        PagedModel<CampaignDTO> result = campaignService.findAll(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().getName())
                .isEqualTo("Campanha Teste all");

        verify(campaingRepository, times(1)).findAllByStatus(StatusCampaign.OPEN, pageable);
        verify(campaignMapper, times(1)).entityToDto(campaign);
    }

    private static MenuCampaignDTO buildMenuDto(Integer id) {
        final var dto = new MenuCampaignDTO();
        dto.setId(id);
        return dto;
    }
}