package dev.caridadems.model;

import dev.caridadems.domain.StatusDonationItemMenuCampaign;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "donation_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationItem extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "menu_campaign_id")
    private MenuCampaign menuCampaign;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.ORDINAL)
    private StatusDonationItemMenuCampaign statusItem;

    private Double quantity;

}
