package dev.caridadems.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donation_item__menu_campaign")
public class DonationItemMenuCampaign extends BaseEntity{

    @OneToOne
    private Donor donor;

    @OneToOne
    private DonationItem donationItem;

    private Double quantity;
}
