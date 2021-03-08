package com.rincentral.test.models.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class ExternalCar {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("segment")
    private String segment;

    @JsonProperty("brand_id")
    private Integer brandId;

    @JsonProperty("model")
    private String model;

    @JsonProperty("generation")
    private String generation;

    @JsonProperty("modification")
    private String modification;
}
