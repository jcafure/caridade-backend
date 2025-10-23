package dev.caridadems.controller;

import dev.caridadems.dto.AddMenuToCampaignRequest;
import dev.caridadems.dto.CampaignDTO;
import dev.caridadems.service.CampaignService;
import lombok.AllArgsConstructor;
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

    @PutMapping("/update-campaign")
    public ResponseEntity<CampaignDTO> updateCampaign(@RequestBody CampaignDTO dto) {
        return ResponseEntity.ok(campaignService.updateCampaign(dto));
    }

    @PutMapping("/cancelled-campaign/{idCampaign}")
    public ResponseEntity<CampaignDTO> cancelledCampaign(@PathVariable Integer idCampaign) {
        campaignService.cancelledCampaign(idCampaign);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{campaignId}/add-menu-to-campaign")
    public ResponseEntity<CampaignDTO> addMenuToCampaign(@PathVariable Integer idCampaign,
                                               @RequestBody AddMenuToCampaignRequest request) {
        return null;

    }

}
