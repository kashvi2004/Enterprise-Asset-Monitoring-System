package com.enterprise.eams.assetmodule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AssetRequestDTO {

    @NotBlank(message = "Name is required")

    private String name;
    @NotBlank(message = "Type is required")
    private String type;
    @NotBlank(message = "Location is required")

    private String location;
    @Positive(message = "Temperature must be positive")

    private double thresholdTemp;
    @Positive(message = "Pressure must be positive")

    private double thresholdPressure;
    @NotNull(message = "Assigned user is required")

    private Long assignedUserId;
}