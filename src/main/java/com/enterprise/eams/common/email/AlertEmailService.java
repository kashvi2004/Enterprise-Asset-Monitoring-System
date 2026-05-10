package com.enterprise.eams.common.email;

import com.enterprise.eams.assetmodule.entity.Asset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AlertEmailService {
    private final EmailService emailService;


    public void sendAlertEmail(String to, String name, Asset asset,
                               double temperature, double pressure, LocalDateTime time) {

        String subject = "🚨 ALERT: Threshold Breach Detected";

        String body =
                "Hello " + name + ",\n\n" +

                        "An alert has been triggered for your assigned asset.\n\n" +

                        "Asset Details:\n" +
                        "- Asset ID: " + asset.getId() + "\n" +
                        "- Type: " + capitalize(asset.getType()) + "\n" +
                        "- Location: " + asset.getLocation() + "\n\n" +

                        "Current Readings:\n" +
                        "- Temperature: " + temperature + "°C\n" +
                        "- Pressure: " + pressure + " bar\n\n" +

                        "Thresholds:\n" +
                        "- Temperature Limit: " + asset.getThresholdTemp() + "\n" +
                        "- Pressure Limit: " + asset.getThresholdPressure() + "\n\n" +

                        "Triggered At: " + time + "\n\n" +

                        "Please take immediate action.\n\n" +

                        "Regards,\nEAMS Team";

        emailService.sendEmail(to, subject, body);
    }

    public void sendResolveEmail(String to, String name, Asset asset) {

        String subject = "✅ Issue Resolved | EAMS";

        String body =
                "Hello " + name + ",\n\n" +

                        "The issue with your assigned asset has been resolved.\n\n" +

                        "Asset Details:\n" +
                        "- Asset ID: " + asset.getId() + "\n" +
                        "- Type: " + capitalize(asset.getType()) + "\n" +
                        "- Location: " + asset.getLocation() + "\n\n" +

                        "All readings are back to normal.\n\n" +

                        "Regards,\nEAMS Team";

        emailService.sendEmail(to, subject, body);
    }
    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0,1).toUpperCase() + text.substring(1).toLowerCase();
    }
}
