package com.enterprise.eams.sensormodule.entity;

import com.enterprise.eams.assetmodule.entity.Asset;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double temperature;
    private double pressure;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

}
