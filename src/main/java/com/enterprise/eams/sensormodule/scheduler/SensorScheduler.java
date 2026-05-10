package com.enterprise.eams.sensormodule.scheduler;


import com.enterprise.eams.assetmodule.entity.Asset;
import com.enterprise.eams.assetmodule.repository.AssetRepository;
import com.enterprise.eams.sensormodule.dto.SensorDataRequestDTO;
import com.enterprise.eams.sensormodule.service.SensorDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class SensorScheduler {

    private final AssetRepository assetRepo;
    private final SensorDataService sensorService;
    private final Random random = new Random();

    @Scheduled(fixedRate = 15000) // every 15 seconds
    public void simulateSensorData() {

        List<Asset> assets = assetRepo.findAll();

        for (Asset asset : assets) {

            // generate realistic values around threshold
            double temp = asset.getThresholdTemp() - 10 + random.nextDouble() * 30;
            double pressure = asset.getThresholdPressure() - 5 + random.nextDouble() * 20;

            SensorDataRequestDTO dto = new SensorDataRequestDTO();
            dto.setTemperature(temp);
            dto.setPressure(pressure);

            sensorService.addSensorData(asset.getId(), dto);

            System.out.println("Simulated → Asset: " + asset.getId()
                    + " Temp: " + temp
                    + " Pressure: " + pressure);
        }
    }
}