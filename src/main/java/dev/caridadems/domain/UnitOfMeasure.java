package dev.caridadems.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public enum UnitOfMeasure {

    KG (0, "Kilogramas"),
    LITROS (1, "Litros"),
    UN(2, "Unidade"),
    GR(3, "Gramas");

    private final Integer id;
    private final String value;

    public static UnitOfMeasure toEnum(String value) {
        if ((value == null)) {
            return null;
        }
        for (UnitOfMeasure unitOfMeasure: UnitOfMeasure.values()) {
            if (value.equals(unitOfMeasure.getValue())){
                return unitOfMeasure;
            }
        }
        return null;
    }
}
