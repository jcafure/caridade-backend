package dev.caridadems.model;

import dev.caridadems.domain.ProductCategory;
import dev.caridadems.domain.UnitOfMeasure;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product extends BaseEntity{

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private UnitOfMeasure unitOfMeasure;

    @Enumerated(EnumType.ORDINAL)
    private ProductCategory productCategory;
}
