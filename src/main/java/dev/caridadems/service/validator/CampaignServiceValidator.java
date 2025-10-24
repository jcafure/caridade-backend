package dev.caridadems.service.validator;

import dev.caridadems.dto.CampaignDTO;
import dev.caridadems.dto.CampaignUpdateDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class CampaignServiceValidator {

    public void validateCreate(CampaignDTO campaignDTO){
        validateName(campaignDTO.getName());
        validateDates(campaignDTO.getDateInit(), campaignDTO.getDateEnd());
    }

    public void validateUpdate(CampaignUpdateDTO updateDTO) {
        validateName(updateDTO.getName());
        validateDates(updateDTO.getDateInit(), updateDTO.getDateInit());
    }

    protected void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome da campanha é obrigatório.");
        }
    }

    protected void validateDates(LocalDate dateInit, LocalDate dateEnd) {
        if (dateInit == null || dateEnd == null) {
            throw new IllegalArgumentException("As datas de início e fim são obrigatórias.");
        }
        if (dateEnd.isBefore(dateInit)) {
            throw new IllegalArgumentException("A data de término deve ser posterior à data de início.");
        }
    }
}
