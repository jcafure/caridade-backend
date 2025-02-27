package dev.caridadems.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    private Double quantity;

}
