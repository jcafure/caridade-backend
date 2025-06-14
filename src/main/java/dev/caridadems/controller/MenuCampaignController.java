package dev.caridadems.controller;

import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.service.MenuCampaignService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/donation-menus")
@AllArgsConstructor
public class MenuCampaignController {

    private final MenuCampaignService menuCampaignService;

    @PostMapping(value = "/new-menu-campaign")
    public ResponseEntity<MenuCampaignDTO> newMenuCampaign(@RequestBody MenuCampaignDTO menuCampaignDTO) {
        return ResponseEntity.ok(menuCampaignService.createMenuCampaign(menuCampaignDTO));
    }
}
