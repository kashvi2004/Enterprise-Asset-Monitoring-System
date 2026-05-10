package com.enterprise.eams.sensormodule.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SensorDataRequestDTO {
    @NotNull
    @Positive
    private double temperature;
    @NotNull
    @Positive
    private double pressure;

}