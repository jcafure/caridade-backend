package dev.caridadems.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCampaign {
    OPEN(0),
    CANCELED(1),
    FINISH(2);

    private Integer value;
}
