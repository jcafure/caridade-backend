package dev.caridadems.service;

import dev.caridadems.dto.CampaignDTO;
import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.mapper.CampaignMapper;
import dev.caridadems.model.Campaign;
import dev.caridadems.repository.CampaingRepository;
import dev.caridadems.repository.MenuCampaignRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CampaignService {

    private final CampaignMapper campaignMapper;
    private final CampaingRepository campaingRepository;
    private final MenuCampaignRepository menuCampaignRepository;

    @Transactional
    public CampaignDTO newCampaing(CampaignDTO campaignDTO) {
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

    public Page<CampaignDTO> findAll(Pageable pageable) {
        Page<Campaign> campaigns;
        campaigns = campaingRepository.findAll(pageable);
        return campaigns.map(campaignMapper::entityToDto);
    }
}
