package com.enterprise.eams.sensormodule.controller;

import com.enterprise.eams.sensormodule.dto.SensorDataRequestDTO;
import com.enterprise.eams.sensormodule.dto.SensorResponseDTO;
import com.enterprise.eams.sensormodule.entity.SensorData;
import com.enterprise.eams.sensormodule.service.SensorDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensor-data")
@RequiredArgsConstructor
public class SensorDataController {

    private final SensorDataService sensorService;

    @PostMapping("/{assetId}")
    public String addData(@PathVariable Long assetId,
                          @Valid @RequestBody SensorDataRequestDTO dto) {

        return sensorService.addSensorData(assetId, dto);
    }

    @GetMapping("/{assetId}")
    public List<SensorResponseDTO> getData(@PathVariable Long assetId) {
        return sensorService.getDataByAsset(assetId);
    }
}