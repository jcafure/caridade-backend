package dev.caridadems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DonorRegisterDTO {

    private String name;
    private String email;
    private String phone;
    private Integer externalId;
    private List<AddressDTO> addressDTOS = new ArrayList<>();
}
