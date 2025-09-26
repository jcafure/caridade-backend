package dev.caridadems.controller;

import dev.caridadems.dto.CampaignDTO;
import dev.caridadems.service.CampaignService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campaigns")
@AllArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @GetMapping
    public ResponseEntity<PagedModel<CampaignDTO>> getAllCampaigns(Pageable pageable) {
        return ResponseEntity.ok(campaignService.findAll(pageable));
    }

    @PostMapping("/new-campaign")
    public ResponseEntity<CampaignDTO> createCampaign(@RequestBody CampaignDTO dto) {
        return ResponseEntity.ok(campaignService.newCampaing(dto));
    }
}
