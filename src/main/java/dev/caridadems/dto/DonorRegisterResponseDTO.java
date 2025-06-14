package dev.caridadems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DonorRegisterResponseDTO {

    private boolean success;
    private Integer id;
    private String name;
}
