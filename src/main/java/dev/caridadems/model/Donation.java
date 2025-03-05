package dev.caridadems.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "donation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donation extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donation donation;

    @ManyToOne
    @JoinColumn(name = "donation_item_id")
    private DonationItem donationItem;

    private LocalDate donationDate;
    private double quantity;
}
