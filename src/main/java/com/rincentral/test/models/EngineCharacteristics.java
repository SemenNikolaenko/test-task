package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EngineCharacteristics {
    @JsonProperty("engine_type")
    private FuelType engineType;
    @JsonProperty("engine_cylinders")
    private EngineType engineCylinders;
    @JsonProperty("engine_displacement")
    private Integer engineDisplacement;
    @JsonProperty("engine_horsepower")
    private Integer engineHorsepower;
}
