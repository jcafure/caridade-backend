package dev.caridadems.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "charityGroup")
    private List<Campaign> campaigns = new ArrayList<>();
}
