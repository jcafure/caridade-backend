package dev.caridadems.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donation_item_menu_campaign")
public class DonationItemMenuCampaign extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @OneToOne
    private DonationItem donationItem;

    private LocalDate donationDate;
    private Double quantity;
}
