package dev.caridadems.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum TypeAddress {

    LOCAL_JOB(0, "Local de trabalho"),
    HOME(1, "Residência"),
    PREPARATION_LOCATION(2, "Local de preparo");

    private Integer id;
    private String value;
}
