package dev.caridadems.service;

import dev.caridadems.domain.StatusCampaign;
import dev.caridadems.dto.CampaignDTO;
import dev.caridadems.dto.CampaignUpdateDTO;
import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.exception.ObjectNotFoundException;
import dev.caridadems.mapper.CampaignMapper;
import dev.caridadems.model.Campaign;
import dev.caridadems.model.MenuCampaign;
import dev.caridadems.repository.CampaingRepository;
import dev.caridadems.repository.MenuCampaignRepository;
import dev.caridadems.service.validator.CampaignServiceValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CampaignServiceTest {

    @Mock
    private CampaignMapper campaignMapper;

    @Mock
    private CampaingRepository campaingRepository;

    @Mock
    private MenuCampaignRepository menuCampaignRepository;

    @Mock
    private CampaignServiceValidator validator;

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

        verify(validator, times(1)).validateCreate(inputDto);

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

        verifyNoMoreInteractions(campaignMapper, campaingRepository, menuCampaignRepository, validator, menu18, menu22);

    }

    @Test
    void test_ShouldCampaignsAllWithStatusOpen() {
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

        Pageable pageable = PageRequest.of(0, 5);
        Page<Campaign> page = new PageImpl<>(List.of(campaign), pageable, 1);
        when(campaingRepository.findAllByStatusIn( List.of(StatusCampaign.OPEN), pageable)).thenReturn(page);
        when(campaignMapper.entityToDto(campaign)).thenReturn(campaignDTO);

        PagedModel<CampaignDTO> result = campaignService.findAll(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().getName()).isEqualTo("Campanha Teste all");

        verify(campaingRepository, times(1)).findAllByStatusIn( List.of(StatusCampaign.OPEN), pageable);
        verify(campaignMapper, times(1)).entityToDto(campaign);
    }

    @Test
    void testShowcancelledCampaign_sucess() {
        final var campaign = new Campaign();
        final var idCampaign = 1;
        campaign.setId(idCampaign);
        campaign.setName("Campanha Teste cancelled");
        campaign.setDescription("Jaimelson");
        campaign.setDateEnd(LocalDate.now());
        campaign.setStatus(StatusCampaign.OPEN);

        when(campaingRepository.findByIdAndStatusIn(idCampaign, List.of(StatusCampaign.OPEN))).thenReturn(Optional.of(campaign));
        campaignService.cancelledCampaign(idCampaign);

        assertEquals(StatusCampaign.CANCELED, campaign.getStatus());
        assertEquals(LocalDate.now(), campaign.getDateEnd());
        verify(campaingRepository, times(1)).save(campaign);
    }

    @Test
    void testFindCampaignByIdSuccess(){
        var idCampaign = 15;
        final var campaign = buildCampaign(idCampaign);
        final var campaignDto = buildDTO(campaign.getName(), "Aberta");

        when(campaingRepository.findByIdAndStatusIn(eq(idCampaign), anyList()))
                .thenReturn(Optional.of(campaign));
        when(campaignMapper.entityToDto(campaign)).thenReturn(campaignDto);

        CampaignDTO result = campaignService.findById(idCampaign);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Campanha Teste cancelled");
    }

    @Test
    void shouldThrowObjectNotFound_whenCampaignNotFoundOrStatusNotAllowed() {
        var id = 99;
        when(campaingRepository.findByIdAndStatusIn(eq(id), anyList()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> campaignService.findById(id))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessageContaining("Campanha com ID " + id + " não encontrado");

        verify(campaingRepository, times(1))
                .findByIdAndStatusIn(eq(id), anyList());
        verifyNoInteractions(campaignMapper);
    }

    @Test
    void updateCampaignRegisters_shouldAddMenus() {
        var idCampaign = 7;
        var existing = new Campaign();
        existing.setId(idCampaign);
        existing.setMenuCampaigns(new ArrayList<>());

        var dtoReq = new CampaignUpdateDTO();
        dtoReq.setName("Campanha Y");
        dtoReq.setIdsMenus(List.of(18, 22));

        var m18 = new MenuCampaign();
        m18.setId(18);
        var m22 = new MenuCampaign();
        m22.setId(22);

        when(campaingRepository.findByIdAndStatusIn(idCampaign, Arrays.asList(StatusCampaign.OPEN, StatusCampaign.FINISH))).thenReturn(Optional.of(existing));
        when(menuCampaignRepository.findById(18)).thenReturn(Optional.of(m18));
        when(menuCampaignRepository.findById(22)).thenReturn(Optional.of(m22));
        when(campaingRepository.save(existing)).thenReturn(existing);

        var dtoResp = new CampaignDTO();
        when(campaignMapper.entityToDto(existing)).thenReturn(dtoResp);

        var result = campaignService.updateCampaignRegisters(idCampaign, dtoReq);

        verify(validator).validateUpdate(dtoReq);
        verify(campaignMapper).applyDtoToEntity(dtoReq, existing);
        verify(menuCampaignRepository, times(1)).findById(18);
        verify(menuCampaignRepository, times(1)).findById(22);
        verify(campaingRepository).save(existing);

        assertThat(existing.getMenuCampaigns()).extracting("id").containsExactlyInAnyOrder(18, 22);
        assertThat(m18.getCampaign()).isEqualTo(existing);
        assertThat(m22.getCampaign()).isEqualTo(existing);

        assertThat(result).isNotNull();
    }

    @Test
    void updateCampaignRegisters_shouldUpdateSimpleFields_whenIdsMenusNullOrEmpty() {
        var idCampaign = 7;
        var existing = new Campaign();
        existing.setId(idCampaign);
        existing.setMenuCampaigns(new java.util.ArrayList<>());

        var dtoReq = new CampaignUpdateDTO();
        dtoReq.setName("Campanha Outubro");
        dtoReq.setDescription("Desc");
        dtoReq.setDateInit(init);
        dtoReq.setDateEnd(end);
        dtoReq.setIdsMenus(null);

        var dtoResp = new CampaignDTO();
        dtoResp.setName("Campanha Outubro");

        when(campaingRepository.findByIdAndStatusIn(idCampaign, Arrays.asList(StatusCampaign.OPEN, StatusCampaign.FINISH)))
                .thenReturn(Optional.of(existing));
        when(campaingRepository.save(existing)).thenReturn(existing);
        when(campaignMapper.entityToDto(existing)).thenReturn(dtoResp);

        var result = campaignService.updateCampaignRegisters(idCampaign, dtoReq);

        verify(validator).validateUpdate(dtoReq);
        verify(campaignMapper).applyDtoToEntity(dtoReq, existing);
        verify(campaingRepository).save(existing);
        verifyNoInteractions(menuCampaignRepository);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Campanha Outubro");
        assertThat(existing.getMenuCampaigns()).isEmpty();
    }


    private static MenuCampaignDTO buildMenuDto(Integer id) {
        final var dto = new MenuCampaignDTO();
        dto.setId(id);
        return dto;
    }

    private Campaign buildCampaign(Integer id) {
        var campaign = new Campaign();
        campaign.setId(id);
        campaign.setName("Campanha Teste cancelled");
        campaign.setDescription("Jaimelson");
        campaign.setDateInit(LocalDate.now());
        campaign.setDateEnd(LocalDate.now().plusDays(5));
        campaign.setStatus(StatusCampaign.OPEN);
        return campaign;
    }

    private CampaignDTO buildDTO(String name, String statusDesc) {
        CampaignDTO dto = new CampaignDTO();
        dto.setName(name);
        dto.setStatus(statusDesc);
        return dto;
    }
}