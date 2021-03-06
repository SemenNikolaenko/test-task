package com.rincentral.test.models.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ExternalCarInfo extends ExternalCar{

    @JsonProperty("year_range")
    private String yearsRange;

    @JsonProperty("engine_type")
    private FuelType fuelType;

    @JsonProperty("engine_cylinders")
    private EngineType engineType;

    @JsonProperty("engine_displacement")
    private Integer engineDisplacement;

    @JsonProperty("engine_horsepower")
    private Integer hp;

    @JsonProperty("gearbox")
    private GearboxType gearboxType;

    @JsonProperty("wheel_drive")
    private WheelDriveType wheelDriveType;

    @JsonProperty("body_length")
    private Integer bodyLength;

    @JsonProperty("body_width")
    private Integer bodyWidth;

    @JsonProperty("body_height")
    private Integer bodyHeight;

    @JsonProperty("body_style")
    private String bodyStyle;

    @JsonProperty("acceleration")
    private Double acceleration;

    @JsonProperty("max_speed")
    private Integer maxSpeed;
}
