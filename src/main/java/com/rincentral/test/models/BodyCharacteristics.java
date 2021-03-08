package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BodyCharacteristics {
    @JsonProperty("body_length")
    Integer bodyLength;
    @JsonProperty("body_width")
    Integer bodyWidth;
    @JsonProperty("body_height")
    Integer bodyHeight;
    @JsonProperty("body_style")
    String bodyStyle;
}
