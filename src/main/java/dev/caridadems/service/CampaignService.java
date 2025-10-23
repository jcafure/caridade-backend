package dev.caridadems.service;

import dev.caridadems.domain.StatusCampaign;
import dev.caridadems.dto.CampaignDTO;
import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.exception.ObjectNotFoundException;
import dev.caridadems.mapper.CampaignMapper;
import dev.caridadems.model.Campaign;
import dev.caridadems.repository.CampaingRepository;
import dev.caridadems.repository.MenuCampaignRepository;
import dev.caridadems.service.validator.CampaignServiceValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

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
        campaignMapper.applyDtoToEntity(campaignDTO, campaignExist);
        validator.validateUpdate(campaignDTO);

        return campaignMapper.entityToDto(campaingRepository.save(campaignExist));
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
