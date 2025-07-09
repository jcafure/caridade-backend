package dev.caridadems.controller;

import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.service.MenuCampaignService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/donation-menus")
@AllArgsConstructor
public class MenuCampaignController {

    private final MenuCampaignService menuCampaignService;

    @PostMapping(value = "/new-menu-campaign")
    public ResponseEntity<MenuCampaignDTO> newMenuCampaign(@RequestBody MenuCampaignDTO menuCampaignDTO) {
        return ResponseEntity.ok(menuCampaignService.createMenuCampaign(menuCampaignDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<MenuCampaignDTO>> getAllMenus(@RequestParam(required = false) String mealType,
                                                             Pageable pageable) {
        return ResponseEntity.ok(menuCampaignService.findAll(mealType, pageable));
    }

    @PutMapping("/update-menus")
    public ResponseEntity<MenuCampaignDTO> update(@RequestBody MenuCampaignDTO menuCampaignDTO) {
        return ResponseEntity.ok(menuCampaignService.updateMenu(menuCampaignDTO));
    }

    @DeleteMapping(value = "/delete-menu/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        menuCampaignService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
