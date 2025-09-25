package dev.caridadems.mapper;

import dev.caridadems.domain.StatusCampaign;
import dev.caridadems.dto.CampaignDTO;
import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.model.Campaign;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

        return campaign;
    }

    public CampaignDTO entityToDto(Campaign entity) {
        var dto = new CampaignDTO();
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus().getDescription());
        dto.setDescription(entity.getDescription());
        dto.setDateInit(entity.getDateInit());
        dto.setDateEnd(entity.getDateEnd());

        List<MenuCampaignDTO> menuCampaignDTOS = safeList(entity.getMenuCampaigns())
                .stream()
                .map(menuCampaignMapper::entityToDto)
                .toList();
        dto.setMenuCampaignDTOS(menuCampaignDTOS);

        return dto;
    }

    private <T> List<T> safeList(List<T> list){
        return Optional.ofNullable(list).orElseGet(List::of);
    }
}
