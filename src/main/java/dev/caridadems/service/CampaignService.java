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
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CampaignService {

    private final CampaignMapper campaignMapper;
    private final CampaingRepository campaingRepository;
    private final MenuCampaignRepository menuCampaignRepository;
    private final CampaignServiceValidator validator;

    @Transactional
    public CampaignDTO newCampaing(CampaignDTO campaignDTO) {
        validator.validateCreate(campaignDTO);
        var entity = campaignMapper.dtoToEntity(campaignDTO);
        var saved = campaingRepository.save(entity);

        if (campaignDTO.getMenuCampaignDTOS() != null && !campaignDTO.getMenuCampaignDTOS().isEmpty()) {
            var menuIds = campaignDTO.getMenuCampaignDTOS().stream()
                    .map(MenuCampaignDTO::getId)
                    .filter(Objects::nonNull)
                    .toList();

            var menus = menuCampaignRepository.findAllById(menuIds);
            menus.forEach(menu -> menu.setCampaign(saved));
            saved.setMenuCampaigns(new ArrayList<>(menus));
        }

        return campaignMapper.entityToDto(saved);
    }

    @Transactional
    public CampaignDTO updateCampaign(CampaignDTO campaignDTO) {
        var campaignExist = findCampaignById(campaignDTO.getId());
        //campaignMapper.applyDtoToEntity(campaignDTO, campaignExist);

        return campaignMapper.entityToDto(campaingRepository.save(campaignExist));
    }

    @Transactional
    public CampaignDTO updateCampaignRegisters(Integer idCampaign, CampaignUpdateDTO dtoRequest) {
        var campaignExist = findCampaignById(idCampaign);
        validator.validateUpdate(dtoRequest);
        campaignMapper.applyDtoToEntity(dtoRequest, campaignExist);

        if (dtoRequest.getIdsmenus() != null && !dtoRequest.getIdsmenus().isEmpty()){
            addMenusToCampaign(campaignExist, dtoRequest.getIdsmenus());
        }

        return campaignMapper.entityToDto(campaingRepository.save(campaignExist));
    }

    public CampaignDTO addMenusToCampaign(Campaign campaign,List<Integer> idsMenus) {

        var ids = Optional.ofNullable(idsMenus).orElseGet(List::of)
                .stream().filter(Objects::nonNull).collect(Collectors.toSet());

        var menusExistsInCampaign = campaign.getMenuCampaigns().stream()
                .map(MenuCampaign::getId).collect(Collectors.toSet());

        var menuIdsToAdd = new HashSet<>(ids);
        menuIdsToAdd.removeAll(menusExistsInCampaign);

        menuIdsToAdd.forEach(id -> {
            var menu = menuCampaignRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("MenuCampaign id=" + id + " não encontrado"));
            menu.setCampaign(campaign);
            campaign.getMenuCampaigns().add(menu);
        });

        return campaignMapper.entityToDto(campaign);
    }

    public PagedModel<CampaignDTO> findAll(Pageable pageable) {
        Page<Campaign> campaigns;
        campaigns = campaingRepository.findAllByStatusIn(Collections.singletonList(StatusCampaign.OPEN), pageable);
        return new PagedModel<>(campaigns.map(campaignMapper::entityToDto));
    }

    @Transactional
    public void cancelledCampaign(Integer idCampaign){
        campaingRepository.findByIdAndStatusIn(idCampaign, Collections.singletonList(StatusCampaign.OPEN))
                .ifPresent(campaign -> {
                    campaign.setStatus(StatusCampaign.CANCELED);
                    campaign.setDateEnd(LocalDate.now());
                    campaingRepository.save(campaign);
                });
    }

    private Campaign findCampaignById(Integer idCampaign) {
        return campaingRepository.findByIdAndStatusIn(idCampaign, Arrays.asList(StatusCampaign.OPEN, StatusCampaign.FINISH))
                .orElseThrow(() -> new ObjectNotFoundException("Campanha com ID " + idCampaign + " não encontrado"));
    }
}
