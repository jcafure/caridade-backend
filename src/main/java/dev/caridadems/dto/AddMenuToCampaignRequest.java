package dev.caridadems.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddMenuToCampaignRequest {

    private List<Integer> idsMenus;
}
