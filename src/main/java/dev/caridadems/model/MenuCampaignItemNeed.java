package dev.caridadems.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu_campaign_item_need")
public class MenuCampaignItemNeed extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_campaign_id")
    private MenuCampaignV2 menuCampaign;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "donation_item_id")
    private DonationItem donationItem;

    @Column(precision = 19, scale = 3, nullable = false)
    private BigDecimal amountNeeded;
}
