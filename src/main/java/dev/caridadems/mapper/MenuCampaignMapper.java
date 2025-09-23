package dev.caridadems.mapper;

import dev.caridadems.domain.StatusDonationItemMenuCampaign;
import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.model.DonationItem;
import dev.caridadems.model.MenuCampaign;
import dev.caridadems.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MenuCampaignMapper {

    private final DonationItemMapper donationItemMapper;
    private final ProductService productService;

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
        List<DonationItem> items = donationItemMapper.convertDtoListToEntity(dto.getDonationItemDTOList());
        items.forEach(donationItem -> donationItem.setMenuCampaign(menuCampaign));
        menuCampaign.getDonationItems().addAll(items);
        return menuCampaign;
    }

    public void applyDtoToEntity(MenuCampaignDTO dto, MenuCampaign managed) {
        managed.setMealType(dto.getName());

        Map<Integer, DonationItem> currentItems = managed.getDonationItems().stream()
                .filter(i -> i.getId() != null)
                .collect(Collectors.toMap(DonationItem::getId, Function.identity()));

        List<DonationItem> updateItems = buildCollectionDonationItem(dto, managed, currentItems);

        managed.getDonationItems().clear();
        managed.getDonationItems().addAll(updateItems);
    }

    private List<DonationItem> buildCollectionDonationItem(MenuCampaignDTO dto,
                                                           MenuCampaign managed,
                                                           Map<Integer, DonationItem> currentItems) {
        return Optional.ofNullable(dto.getDonationItemDTOList())
                .orElseGet(List::of)
                .stream()
                .map(dto1 -> {
                    DonationItem target = (dto1.getId() != null && currentItems.containsKey(dto1.getId()))
                            ? currentItems.remove(dto1.getId())
                            : createNewDonationItem(managed);
                    target.setProduct(productService.findById(dto1.getProductDTO().getId()));
                    target.setQuantity(dto1.getQuantity());
                    target.setStatusItem(StatusDonationItemMenuCampaign.FOR_DONATED);
                    return target;
                }).collect(Collectors.toList());
    }

    private DonationItem createNewDonationItem(MenuCampaign managed) {
        var newItem = new DonationItem();
        newItem.setMenuCampaign(managed);
        return newItem;
    }
}
