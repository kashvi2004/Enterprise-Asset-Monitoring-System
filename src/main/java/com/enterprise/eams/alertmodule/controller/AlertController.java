package com.enterprise.eams.alertmodule.controller;

import com.enterprise.eams.alertmodule.dto.AlertResponseDTO;
import com.enterprise.eams.alertmodule.dto.AlertUpdateDTO;
import com.enterprise.eams.alertmodule.entity.Alert;
import com.enterprise.eams.alertmodule.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertRepository alertRepo;

    // GET all alerts
    @GetMapping
    public List<AlertResponseDTO> getAllAlerts() {
        return alertRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // GET alerts by asset
    @GetMapping("/asset/{assetId}")
    public List<AlertResponseDTO> getAlertsByAsset(@PathVariable Long assetId) {
        return alertRepo.findAll().stream()
                .filter(a -> a.getAsset().getId().equals(assetId))
                .map(this::mapToDTO)
                .toList();
    }

    // UPDATE alert
    @PutMapping("/{id}")
    public String updateAlert(@PathVariable Long id,
                              @RequestBody AlertUpdateDTO dto) {

        Alert alert = alertRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setStatus(dto.getStatus());
        alertRepo.save(alert);

        return "Alert updated";
    }

    // mapper
    private AlertResponseDTO mapToDTO(Alert alert) {
        return AlertResponseDTO.builder()
                .id(alert.getId())
                .assetId(alert.getAsset().getId())
                .assetName(alert.getAsset().getName())
                .type(alert.getType())
                .message(alert.getMessage())
                .status(alert.getStatus())
                .triggeredAt(alert.getTriggeredAt())
                .build();
    }
}