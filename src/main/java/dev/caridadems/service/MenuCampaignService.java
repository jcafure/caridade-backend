package dev.caridadems.service;

import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.mapper.DonationItemMapper;
import dev.caridadems.mapper.MenuCampaignMapper;
import dev.caridadems.model.DonationItem;
import dev.caridadems.model.MenuCampaign;
import dev.caridadems.repository.MenuCampaignRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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

    private MenuCampaign buildMenuCampaign(MenuCampaignDTO dto) {
        var menuCampaign = new MenuCampaign();
        menuCampaign.setMealType(dto.getName());
        menuCampaign.setDonationItems(buildDonationItens(dto));
        return menuCampaign;
    }

    private List<DonationItem> buildDonationItens(MenuCampaignDTO dto) {
        return dto.getDonationItemDTOList().stream()
                .map(donationDto -> {
                    var product = productService.findById(donationDto.getProductDTO().getId());
                    return donationItemMapper.dtoToEntity(donationDto, product);
                }).toList();
    }
}
