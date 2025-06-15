package dev.caridadems.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DonationItemDTO implements Serializable {

    private Integer id;

    @JsonProperty("productDto")
    private ProductDTO productDTO;
    private String statusItem;
    private Double quantity;
}
