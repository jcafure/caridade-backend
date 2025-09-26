package dev.caridadems.service.validator;

import dev.caridadems.dto.CampaignDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

class CampaignServiceValidatorTest {

    @InjectMocks
    private CampaignServiceValidator campaignServiceValidator;

    private CampaignDTO campaignDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        campaignDto = new CampaignDTO();
    }

    @Test
    void validateCreate_shouldThrownExceptionWhenNameIsNull() {
        campaignDto.setName(null);
        campaignDto.setDateInit(LocalDate.now());
        campaignDto.setDateEnd(LocalDate.now().plusDays(1));
        Assertions.assertThatThrownBy(() ->
                campaignServiceValidator.validateCreate(campaignDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O nome da campanha é obrigatório.");

    }

    @Test
    void validateCreate_shouldThrownExceptionWhenNameIsBlank() {
        campaignDto.setName("   ");
        campaignDto.setDateInit(LocalDate.now());
        campaignDto.setDateEnd(LocalDate.now().plusDays(1));
        Assertions.assertThatThrownBy(() ->
                        campaignServiceValidator.validateCreate(campaignDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O nome da campanha é obrigatório.");

    }

    @Test
    void validateCreate_shouldThrownExceptionWhenDateInitIsNull() {
        campaignDto.setName("Teste Jaimilson");
        campaignDto.setDateInit(null);
        campaignDto.setDateEnd(LocalDate.now().plusDays(1));
        Assertions.assertThatThrownBy(() ->
                        campaignServiceValidator.validateCreate(campaignDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("As datas de início e fim são obrigatórias.");

    }

    @Test
    void validateCreate_shouldThrownExceptionWhenDateEndIsNull() {
        campaignDto.setName("Teste Jaimilson");
        campaignDto.setDateInit(LocalDate.now());
        campaignDto.setDateEnd(null);
        Assertions.assertThatThrownBy(() ->
                        campaignServiceValidator.validateCreate(campaignDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("As datas de início e fim são obrigatórias.");

    }

    @Test
    void validateCreate_shouldThrownExceptionWhenDateEndIsBeforeDateInit() {
        campaignDto.setName("Teste Jaimilson");
        campaignDto.setDateInit(LocalDate.now());
        campaignDto.setDateEnd(LocalDate.now().minusDays(1));
        Assertions.assertThatThrownBy(() ->
                        campaignServiceValidator.validateCreate(campaignDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A data de término deve ser posterior à data de início.");

    }
}