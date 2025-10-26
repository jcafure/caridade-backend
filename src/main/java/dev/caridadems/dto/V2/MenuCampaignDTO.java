package dev.caridadems.dto.V2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuCampaignDTO {
    private Integer menuCampaignId; // id do vínculo v2
    private Integer menuId;
    private String title;           // do Menu
    private String mealType;        // do Menu
    private Integer qtyPlanned;     // do vínculo
    private String notes;           // do vínculo

    // opcionais (se quiser expor necessidades)
    private List<MenuCampaignItemNeedDTO> needs;
}
