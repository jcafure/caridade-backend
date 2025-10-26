package dev.caridadems.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "campaign_menu_donation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuCampaignDonation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_campaign_id")
    private MenuCampaignV2 menuCampaign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "donation_item_id")
    private DonationItem donationItem;

    private LocalDate donationDate;

    @Column(precision = 19, scale = 3, nullable = false)
    private BigDecimal quantity;

    private String notes;
}
