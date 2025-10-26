package dev.caridadems.dto.V2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
public class MenuCampaignItemNeedDTO {
    private Integer donationItemId;
    private BigDecimal amountNeeded;
}
