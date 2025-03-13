package dev.caridadems.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public enum UnitOfMeasure {

    KG (0, "Kilogramas"),
    lT (1, "Litros"),
    UN(2, "Unidade"),
    GR(3, "Gramas");

    private final Integer id;
    private final String value;
}
