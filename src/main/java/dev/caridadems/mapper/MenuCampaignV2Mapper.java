package dev.caridadems.mapper;

import dev.caridadems.dto.V2.MenuCampaignDTO;
import dev.caridadems.dto.V2.MenuCampaignItemNeedDTO;
import dev.caridadems.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuCampaignV2Mapper {

    public MenuCampaignV2 toEntity(MenuCampaignDTO dto, Campaign campaign, Menu menu) {
        var mc = new MenuCampaignV2();
        if (dto.getMenuCampaignId() != null) mc.setId(dto.getMenuCampaignId());
        mc.setCampaign(campaign);
        mc.setMenu(menu);
        mc.setQtyPlanned(dto.getQtyPlanned());
        mc.setNotes(dto.getNotes());

        if (dto.getNeeds() != null) {
            for (var n : dto.getNeeds()) {
                var need = new MenuCampaignItemNeed();
                need.setMenuCampaign(mc);
                var itemRef = new DonationItem(); itemRef.setId(n.getDonationItemId());
                need.setDonationItem(itemRef);
                need.setAmountNeeded(n.getAmountNeeded());
                mc.getItemNeeds().add(need);
            }
        }
        return mc;
    }

    public MenuCampaignDTO toDto(MenuCampaignV2 mc, boolean includeNeeds) {
        if (mc == null) return null;

        var dto = new MenuCampaignDTO();
        dto.setMenuCampaignId(mc.getId());
        dto.setQtyPlanned(mc.getQtyPlanned());
        dto.setNotes(mc.getNotes());

        var menu = mc.getMenu();
        if (menu != null) {
            dto.setMenuId(menu.getId());
            dto.setTitle(menu.getTitle());
            dto.setMealType(menu.getMealType().getValue());
        }

        if (includeNeeds) {
            List<MenuCampaignItemNeedDTO> needs = mc.getItemNeeds() == null ? List.<MenuCampaignItemNeedDTO>of()
                    : mc.getItemNeeds().stream()
                    .map(this::toNeedDto)
                    .toList();
            dto.setNeeds(needs);
        }

        return dto;
    }

    private MenuCampaignItemNeedDTO toNeedDto(MenuCampaignItemNeed need) {
        var dto = new MenuCampaignItemNeedDTO();
        dto.setDonationItemId(need.getDonationItem().getId());
        dto.setAmountNeeded(need.getAmountNeeded());
        return dto;
    }
}
