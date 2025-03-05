package dev.caridadems.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "donor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donor extends BaseEntity{

    private String name;
    private String email;
    private String phone;
    private String address;
}
