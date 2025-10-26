package dev.caridadems.dto.V2;

import dev.caridadems.dto.MenuCampaignDTO;

import java.time.LocalDate;
import java.util.List;

public class CampaignDTO {
    private Integer id;
    private String name;
    private String description;
    private LocalDate dateInit;
    private LocalDate dateEnd;
    private String status;
    private List<MenuCampaignDTO> menuCampaignDTOS;
}
