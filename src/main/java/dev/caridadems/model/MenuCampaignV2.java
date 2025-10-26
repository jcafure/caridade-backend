package dev.caridadems.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "menu_campaign_v2")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuCampaignV2 extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    /*
     * necessidades planejadas
     *  para este menu nesta campanha*/
    @OneToMany(mappedBy = "menuCampaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MenuCampaignItemNeed> itemNeeds = new HashSet<>();

    /* doações efetivas recebidas para este menu nesta campanha/*/
    @OneToMany(mappedBy = "menuCampaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MenuCampaignDonation> donations = new HashSet<>();

    private Integer qtyPlanned;
    private String notes;

    public void addNeed(MenuCampaignItemNeed need) {
        itemNeeds.add(need);
        need.setMenuCampaign(this);
    }
    public void removeNeed(MenuCampaignItemNeed need) {
        itemNeeds.remove(need);
        need.setMenuCampaign(null);
    }

    public void addDonation(MenuCampaignDonation donation) {
        donations.add(donation);
        donation.setMenuCampaign(this);
    }
    public void removeDonation(MenuCampaignDonation donation) {
        donations.remove(donation);
        donation.setMenuCampaign(null);
    }
}
