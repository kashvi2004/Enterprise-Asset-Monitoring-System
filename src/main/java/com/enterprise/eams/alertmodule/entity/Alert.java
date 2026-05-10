package com.enterprise.eams.alertmodule.entity;

import com.enterprise.eams.assetmodule.entity.Asset;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "asset_id")
        private Asset asset;

        private String type; // TEMP_HIGH / PRESSURE_HIGH

        private String message;

        private String status; // ACTIVE / RESOLVED

        private LocalDateTime triggeredAt;

}
