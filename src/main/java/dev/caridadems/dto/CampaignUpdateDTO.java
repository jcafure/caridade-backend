package dev.caridadems.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CampaignUpdateDTO {
    private String name;
    private String description;
    private LocalDate dateInit;
    private LocalDate dateEnd;
    private List<Integer> idsmenus = new ArrayList<>();
}
