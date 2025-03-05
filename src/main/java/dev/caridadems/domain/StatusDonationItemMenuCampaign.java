package dev.caridadems.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusDonationItemMenuCampaign {
    DONATED (0, "Doado"),
    PARTIALLY_DONATED(1,"Parcialmente Doado"),
    FOR_DONATED(2,"Para doar");

    private final Integer id;
    private final String value;
}
