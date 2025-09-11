package dev.caridadems.service;

import dev.caridadems.dto.CampaignDTO;
import dev.caridadems.mapper.CampaignMapper;
import dev.caridadems.repository.CampaingRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CampaingService {

    private final CampaignMapper campaignMapper;
    private final CampaingRepository campaingRepository;

    @Transactional
    public CampaignDTO newCampaing(CampaignDTO campaignDTO) {
        return campaignMapper.entityToDto(campaingRepository.save(campaignMapper.dtoToEntity(campaignDTO)));
    }
}
