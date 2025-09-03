package dev.caridadems.service;

import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.exception.ObjectNotFoundException;
import dev.caridadems.mapper.MenuCampaignMapper;
import dev.caridadems.model.MenuCampaign;
import dev.caridadems.repository.MenuCampaignRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class MenuCampaignService {

    private final MenuCampaignRepository menuCampaignRepository;
    private final MenuCampaignMapper menuCampaignMapper;

    @Transactional
    public MenuCampaignDTO createMenuCampaign(MenuCampaignDTO dto) {
        return menuCampaignMapper.entityToDto(menuCampaignRepository.save(menuCampaignMapper.convertDtoToEntity(dto)));
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

    @Transactional
    public MenuCampaignDTO updateMenu(MenuCampaignDTO menuCampaignDTO) {
        if (!menuCampaignRepository.existsById(menuCampaignDTO.getId())){
            throw new ObjectNotFoundException("Menu com id: " + menuCampaignDTO.getId() + " não encontrado");
        }
        return menuCampaignMapper.entityToDto(menuCampaignRepository.save(menuCampaignMapper.convertDtoToEntity(menuCampaignDTO)));
    }

    public void delete(Integer id) {
        menuCampaignRepository.deleteById(id);
    }

    public MenuCampaignDTO findById(Integer id) {
        MenuCampaign menuCampaign = menuCampaignRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("MenuCampaign com ID " + id + " não encontrado"));
        return menuCampaignMapper.entityToDto(menuCampaign);
    }
}
