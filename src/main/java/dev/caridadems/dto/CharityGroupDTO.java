package dev.caridadems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CharityGroupDTO implements Serializable {

    private Integer id;
    private String name;
    private String description;
    private String phone;
    private String email;
    private List<AddressDTO> address;
}
