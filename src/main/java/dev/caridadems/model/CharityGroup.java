package dev.caridadems.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "charity_group")
@Data
@EqualsAndHashCode(callSuper = true)
public class CharityGroup extends BaseEntity {

    private String name;
    private String description;
    private String address;
    private String phone;
    private String email;
}
