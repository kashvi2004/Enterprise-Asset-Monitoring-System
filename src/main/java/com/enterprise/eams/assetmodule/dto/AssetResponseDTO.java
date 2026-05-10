package com.enterprise.eams.assetmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponseDTO {

    private Long id;
    private String name;
    private String type;
    private String location;

    private double thresholdTemp;
    private double thresholdPressure;

    private String assignedUserName;
    private String status;
}