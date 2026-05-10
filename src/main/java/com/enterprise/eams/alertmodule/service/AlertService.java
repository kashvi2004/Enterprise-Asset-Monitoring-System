package com.enterprise.eams.alertmodule.service;

import com.enterprise.eams.alertmodule.entity.Alert;
import com.enterprise.eams.alertmodule.repository.AlertRepository;
import com.enterprise.eams.assetmodule.entity.Asset;
import com.enterprise.eams.common.email.AlertEmailService;
import com.enterprise.eams.sensormodule.dto.SensorDataRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertEmailService emailService;
    private final AlertRepository alertRepo;

    // per-user cooldown tracking
    private Map<String, LocalDateTime> userCooldownMap = new HashMap<>();

    public void processAlert(Asset asset,
                             String previousStatus,
                             String newStatus,
                             SensorDataRequestDTO dto) {

        if (asset.getAssignedTo() == null) return;

        // when entering critical
        if ("CRITICAL".equals(newStatus) && !"CRITICAL".equals(previousStatus)) {

            LocalDateTime now = LocalDateTime.now();

            // check existing active alert
            Optional<Alert> existingAlert =
                    alertRepo.findByAsset_IdAndStatus(asset.getId(), "ACTIVE");

            if (existingAlert.isPresent()) {

                // update existing alert (no new role, no email)
                Alert alert = existingAlert.get();
                alert.setMessage("Threshold crossed for asset " + asset.getName());
                alert.setTriggeredAt(now);
                alert.setType("UPDATED_BREACH");

                alertRepo.save(alert);

            } else {

                // create new alert
                Alert alert = new Alert();
                alert.setAsset(asset);
                alert.setType("THRESHOLD_BREACH");
                alert.setMessage("Threshold crossed for asset " + asset.getName());
                alert.setStatus("ACTIVE");
                alert.setTriggeredAt(now);

                alertRepo.save(alert);

                // cooldown based email
                String email = asset.getAssignedTo().getEmail();
                LocalDateTime lastTime = userCooldownMap.get(email);

                if (lastTime == null || now.isAfter(lastTime.plusMinutes(2))) {

                    emailService.sendAlertEmail(
                            email,
                            asset.getAssignedTo().getName(),
                            asset,
                            dto.getTemperature(),
                            dto.getPressure(),
                            now
                    );

                    userCooldownMap.put(email, now);
                }
            }
        }

        // when resolved
        if ("CRITICAL".equals(previousStatus) && "NORMAL".equals(newStatus)) {

            Optional<Alert> activeAlert =
                    alertRepo.findByAsset_IdAndStatus(asset.getId(), "ACTIVE");

            if (activeAlert.isPresent()) {
                Alert alert = activeAlert.get();
                alert.setStatus("RESOLVED");
                alertRepo.save(alert);
            }

            // send resolve email
            emailService.sendResolveEmail(
                    asset.getAssignedTo().getEmail(),
                    asset.getAssignedTo().getName(),
                    asset
            );
        }
    }
}