package dev.caridadems.mapper;

import dev.caridadems.domain.StatusCampaign;
import dev.caridadems.dto.CampaignDTO;
import dev.caridadems.model.Campaign;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CampaignMapper {


    private final MenuCampaignMapper menuCampaignMapper;

    public Campaign dtoToEntity(CampaignDTO dto){
        var campaign = new Campaign();
        campaign.setName(dto.getName());
        campaign.setDescription(dto.getDescription());
        campaign.setStatus(StatusCampaign.toEnum(dto.getStatus()));
        campaign.setDateInit(dto.getDateInit());
        campaign.setDateEnd(dto.getDateEnd());
        campaign.setMenuCampaigns(menuCampaignMapper.convertDtoToEntityList(dto.getMenuCampaignDTOS()));
        return campaign;
    }

    public CampaignDTO entityToDto(Campaign entity) {
        var dto = new CampaignDTO();
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus().getDescription());
        dto.setDescription(entity.getDescription());
        dto.setMenuCampaignDTOS(menuCampaignMapper.convertEntityToDtoList(entity.getMenuCampaigns()));
        dto.setDateInit(entity.getDateInit());
        dto.setDateEnd(entity.getDateEnd());
        return dto;
    }

}
