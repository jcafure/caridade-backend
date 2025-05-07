package dev.caridadems.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    private Integer externalId;
    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> address;

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DonationItemMenuCampaign> donationItemMenuCampaigns;
}
