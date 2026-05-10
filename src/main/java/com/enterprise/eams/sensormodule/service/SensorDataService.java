package com.enterprise.eams.sensormodule.service;

import com.enterprise.eams.alertmodule.service.AlertService;
import com.enterprise.eams.assetmodule.entity.Asset;
import com.enterprise.eams.assetmodule.repository.AssetRepository;
import com.enterprise.eams.sensormodule.dto.SensorDataRequestDTO;
import com.enterprise.eams.sensormodule.dto.SensorResponseDTO;
import com.enterprise.eams.sensormodule.entity.SensorData;
import com.enterprise.eams.sensormodule.repository.SensorDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorDataService {

    private final SensorDataRepository sensorRepo;
    private final AssetRepository assetRepo;
    private final AlertService alertService;

    public String addSensorData(Long assetId, SensorDataRequestDTO dto) {

        Asset asset = assetRepo.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        //data storage logic
        SensorData data = new SensorData();
        data.setTemperature(dto.getTemperature());
        data.setPressure(dto.getPressure());
        data.setTimestamp(LocalDateTime.now());
        data.setAsset(asset);

        sensorRepo.save(data);

        //alert logic
        double currentTemp = dto.getTemperature();
        double currentPressure = dto.getPressure();

        double tempThreshold = asset.getThresholdTemp();
        double pressureThreshold = asset.getThresholdPressure();

        boolean isTempCritical = currentTemp > (tempThreshold + 5);
        boolean isTempNormal = currentTemp < (tempThreshold - 5);

        boolean isPressureCritical = currentPressure > (pressureThreshold + 5);
        boolean isPressureNormal = currentPressure < (pressureThreshold - 5);


        //store previous states
        String previousStatus = asset.getStatus();

        //determine new status
        String newStatus=previousStatus;
        if (isTempCritical || isPressureCritical) {
            newStatus = "CRITICAL";
        } else if (isTempNormal && isPressureNormal) {
            newStatus = "NORMAL";
        }



        //update status
        asset.setStatus(newStatus);

        //save status if changed
        if (!newStatus.equals(previousStatus)) {
            asset.setStatus(newStatus);
            assetRepo.save(asset);
        }

        //trigger alert logic
       alertService.processAlert(asset,previousStatus,newStatus,dto);
        return "Sensor data recorded";
    }


    //show all readings of an asset
    public List<SensorResponseDTO> getDataByAsset(Long assetId) {

        return sensorRepo.findByAsset_IdOrderByTimestampDesc(assetId)
                .stream()
                .map(data -> SensorResponseDTO.builder()
                        .id(data.getId())
                        .temperature(data.getTemperature())
                        .pressure(data.getPressure())
                        .timestamp(data.getTimestamp())
                        .build())
                .toList();
    }


}