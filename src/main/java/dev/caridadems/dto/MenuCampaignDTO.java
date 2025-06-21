package dev.caridadems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuCampaignDTO implements Serializable {

    private Integer id;
    private String name;
    private List<DonationItemDTO> donationItemDTOList;
}
