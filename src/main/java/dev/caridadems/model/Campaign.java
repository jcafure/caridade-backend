package dev.caridadems.model;

import dev.caridadems.domain.StatusCampaign;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "campaign")
public class Campaign extends BaseEntity{

    private String name;
    private String description;
    private LocalDate dateInit;
    private LocalDate dateEnd;

    @Enumerated(EnumType.ORDINAL)
    private StatusCampaign status;

    @ManyToOne
    @JoinColumn(name = "charity_group_id")
    private CharityGroup charityGroup;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DonationItem> donationItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "menu_campaign_id")
    private MenuCampaign menuCampaigns;
}
