package dev.caridadems.controller;

import dev.caridadems.dto.CampaignDTO;
import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.service.CampaingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campaigns")
@AllArgsConstructor
public class CampaignController {

    private final CampaingService campaingService;

    @PostMapping("/new-campaign")
    public ResponseEntity<CampaignDTO> createCampaign(@RequestBody CampaignDTO dto) {
        return ResponseEntity.ok(campaingService.newCampaing(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<CampaignDTO>> getAllCampaigns(Pageable pageable) {
        return ResponseEntity.ok(campaingService.findAll(pageable));
    }
}
