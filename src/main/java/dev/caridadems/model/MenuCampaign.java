package dev.caridadems.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuCampaign extends BaseEntity{

    private String mealType;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @OneToMany(mappedBy = "menuCampaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DonationItem> donationItems = new ArrayList<>();
}
