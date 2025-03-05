package dev.caridadems.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DonationType {

    DELIVERY(0, "Entrega"),
    DELIVERY_LOCAL(1, "Ir ao local de Entrega"),
    PIX(2, "Transferência via Pix");

    private Integer id;
    private String value;
}
