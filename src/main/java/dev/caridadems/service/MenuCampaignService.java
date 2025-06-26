package dev.caridadems.service;

import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.mapper.DonationItemMapper;
import dev.caridadems.mapper.MenuCampaignMapper;
import dev.caridadems.model.DonationItem;
import dev.caridadems.model.MenuCampaign;
import dev.caridadems.model.Product;
import dev.caridadems.repository.MenuCampaignRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuCampaignService {

    private final ProductService productService;
    private final MenuCampaignRepository menuCampaignRepository;
    private final DonationItemMapper donationItemMapper;
    private final MenuCampaignMapper menuCampaignMapper;

    @Transactional
    public MenuCampaignDTO createMenuCampaign(MenuCampaignDTO dto) {
        return menuCampaignMapper.entityToDto(menuCampaignRepository.save(buildMenuCampaign(dto)));
    }

    public Page<MenuCampaignDTO> findAll(String mealType, Pageable pageable) {
        Page<MenuCampaign> menus;

        if(mealType != null && !mealType.isBlank()){
            menus = menuCampaignRepository.findByMealTypeContainingIgnoreCase(mealType, pageable);
        }else{
            menus = menuCampaignRepository.findAll(pageable);
        }
        return menus.map(menuCampaignMapper::entityToDto);
    }

    private MenuCampaign buildMenuCampaign(MenuCampaignDTO dto) {
        var menuCampaign = new MenuCampaign();
        menuCampaign.setMealType(dto.getName());
        menuCampaign.setDonationItems(buildDonationItens(dto, menuCampaign));
        return menuCampaign;
    }

    private List<DonationItem> buildDonationItens(MenuCampaignDTO dto, MenuCampaign entity) {
        return dto.getDonationItemDTOList().stream()
                .map(donationDto -> {
                    var product = productService.findById(donationDto.getProductDTO().getId());
                    var donationItem = donationItemMapper.dtoToEntity(donationDto, product);
                    donationItem.setMenuCampaign(entity);
                    return donationItem;
                }).toList();
    }
}
