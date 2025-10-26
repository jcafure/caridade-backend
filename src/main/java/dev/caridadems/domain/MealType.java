package dev.caridadems.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MealType {

    LUNCH(0, "Almoço");

    private Integer id;
    private String value;
}
