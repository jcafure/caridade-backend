package dev.caridadems.model;

import dev.caridadems.domain.TypeAddress;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "donor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donor extends BaseEntity{

    private String name;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> address;

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DonationItemMenuCampaign> donationItemMenuCampaigns;
}
