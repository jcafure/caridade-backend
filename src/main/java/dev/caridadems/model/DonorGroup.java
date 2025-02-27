package dev.caridadems.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "donor_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorGroup extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "charity_group_id")
    private CharityGroup charityGroupId;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donorId;
}
