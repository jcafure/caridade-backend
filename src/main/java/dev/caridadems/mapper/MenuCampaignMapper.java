package dev.caridadems.mapper;

import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.model.MenuCampaign;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class MenuCampaignMapper {

    private final DonationItemMapper donationItemMapper;

    public MenuCampaignDTO entityToDto(MenuCampaign entity){
        var dto = new MenuCampaignDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getMealType());
        dto.setDonationItemDTOList(donationItemMapper.entityToDto(entity.getDonationItems()));

        return dto;
    }

    public MenuCampaign convertDtoToEntity(MenuCampaignDTO dto) {
        var menuCampaign = new MenuCampaign();
        if (dto.getId() != null) {
            menuCampaign.setId(dto.getId());
        }
        menuCampaign.setMealType(dto.getName());
        menuCampaign.setDonationItems(donationItemMapper.convertDtoListToEntity(dto.getDonationItemDTOList(), menuCampaign));
        return menuCampaign;
    }

    public List<MenuCampaign> convertDtoToEntityList(List<MenuCampaignDTO> menuCampaignDTOS) {
        if (menuCampaignDTOS == null || menuCampaignDTOS.isEmpty()) {
            return List.of();
        }

        return menuCampaignDTOS.stream()
                .map(this::convertDtoToEntity)
                .toList();
    }

    public List<MenuCampaignDTO> convertEntityToDtoList(List<MenuCampaign> menusCampaign) {
        if (menusCampaign == null || menusCampaign.isEmpty()) {
            return List.of();
        }

        return menusCampaign.stream()
                .map(this::entityToDto)
                .toList();
    }
}
