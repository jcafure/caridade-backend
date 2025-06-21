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

    public static StatusDonationItemMenuCampaign toEnum (String value) {
        if(value == null ){
            return null;
        }
        for (var statusMenu : StatusDonationItemMenuCampaign.values()) {
            if (value.equals(statusMenu.getValue())){
                return statusMenu;
            }
        }
        return null;
    }
}
