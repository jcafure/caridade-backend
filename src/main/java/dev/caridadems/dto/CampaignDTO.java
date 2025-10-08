package dev.caridadems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDTO {

    private String name;
    private String description;
    private LocalDate dateInit;
    private LocalDate dateEnd;
    private String status;
    List<MenuCampaignDTO> menuCampaignDTOS;

}
