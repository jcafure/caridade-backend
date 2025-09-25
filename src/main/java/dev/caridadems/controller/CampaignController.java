package dev.caridadems.controller;

import dev.caridadems.dto.CampaignDTO;
import dev.caridadems.service.CampaingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaigns")
@AllArgsConstructor
public class CampaignController {

    private final CampaingService campaingService;

    @PostMapping("/new-campaign")
    public ResponseEntity<CampaignDTO> createCampaign(@RequestBody CampaignDTO dto) {
        return ResponseEntity.ok(campaingService.newCampaing(dto));
    }
}
