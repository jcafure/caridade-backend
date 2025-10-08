package dev.caridadems.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCampaign {
    OPEN(0, "Aberta"),
    CANCELED(1, "Cancelada"),
    FINISH(2, "Finalizada");

    private Integer id;
    private String description;

    public static StatusCampaign toEnum(String label) {
        for (StatusCampaign type : StatusCampaign.values()) {
            if (type.getDescription().equalsIgnoreCase(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de status inválido: " + label);
    }
}
