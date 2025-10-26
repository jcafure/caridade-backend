package dev.caridadems.model;

import dev.caridadems.domain.MealType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu extends BaseEntity{

    private String title;

    @Enumerated(EnumType.ORDINAL)
    private MealType mealType;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MenuCampaignV2> menuCampaigns = new HashSet<>();

    public void addMenuCampaign(MenuCampaignV2 mc) {
        menuCampaigns.add(mc);
        mc.setMenu(this);
    }
    public void removeMenuCampaign(MenuCampaignV2 mc) {
        menuCampaigns.remove(mc);
        mc.setMenu(null);
    }
}
