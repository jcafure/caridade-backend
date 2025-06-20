package dev.caridadems.mapper;

import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.model.MenuCampaign;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MenuCampaignMapper {

    private DonationItemMapper donationItemMapper;

    public MenuCampaignDTO entityToDto(MenuCampaign entity){
        var dto = new MenuCampaignDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getMealType());
        dto.setDonationItemDTOList(donationItemMapper.entityToDto(entity.getDonationItems()));

        return dto;
    }
}
