package dev.caridadems.service.validator;

import dev.caridadems.dto.CampaignDTO;
import org.springframework.stereotype.Component;

@Component
public class CampaignServiceValidator {

    public void validateCreate(CampaignDTO campaignDTO){
        validateName(campaignDTO);
        validateDates(campaignDTO);
    }

    protected void validateName(CampaignDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("O nome da campanha é obrigatório.");
        }
    }

    protected void validateDates(CampaignDTO dto) {
        if (dto.getDateInit() == null || dto.getDateEnd() == null) {
            throw new IllegalArgumentException("As datas de início e fim são obrigatórias.");
        }
        if (dto.getDateEnd().isBefore(dto.getDateInit())) {
            throw new IllegalArgumentException("A data de término deve ser posterior à data de início.");
        }
    }
}
