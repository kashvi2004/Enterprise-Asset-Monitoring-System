package com.enterprise.eams.sensormodule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorResponseDTO {

    private Long id;
    private double temperature;
    private double pressure;
    private LocalDateTime timestamp;
}