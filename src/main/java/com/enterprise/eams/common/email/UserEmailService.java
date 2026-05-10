package com.enterprise.eams.common.email;
import com.enterprise.eams.assetmodule.entity.Asset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserEmailService {



    private final EmailService emailService;

    public void sendWelcomeEmail(String to, String name, String role) {

        String subject = "Welcome to EAMS 🎉";

        String html = "<div style='font-family: Arial, sans-serif; padding:20px;'>"
                + "<h2 style='color:#2c3e50;'>Welcome to EAMS 🚀</h2>"
                + "<p>Hello <b>" + name + "</b>,</p>"
                + "<p>You have been successfully registered as <b>" + role + "</b>.</p>"
                + "<p>We’re excited to have you onboard!</p>"
                + "<hr>"
                + "<p style='color:gray;'>Regards,<br><b>EAMS Team</b></p>"
                + "</div>";

        emailService.sendEmail(to, subject, html);
    }

    public void sendAssetAssignedEmail(String to, String name, Asset asset) {

        String subject = "New Asset Assigned | EAMS";

        String body =
                "Hello " + name + ",\n\n" +

                        "You have been assigned a new asset in EAMS.\n\n" +

                        "Asset Details:\n" +
                        "- Asset ID: " + asset.getId() + "\n" +
                        "- Type: " + capitalize(asset.getType()) + "\n" +
                        "- Location: " + asset.getLocation() + "\n\n" +



                        "Please ensure proper monitoring of this asset.\n\n" +

                        "Regards,\nEAMS Team";

        emailService.sendEmail(to, subject, body);
    }

    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0,1).toUpperCase() + text.substring(1).toLowerCase();
    }

    //to the one whose asset has been snatched
    public void sendReassignmentEmail(String to, String name, Asset asset) {

        String subject = "Asset Reassigned | EAMS";

        String body =
                "Hello " + name + ",\n\n" +

                        "This is to inform you that an asset previously assigned to you has now been reassigned.\n\n" +

                        "Asset Details:\n" +
                        "- Asset ID: " + asset.getId() + "\n" +
                        "- Type: " + capitalize(asset.getType()) + "\n" +
                        "- Location: " + asset.getLocation() + "\n\n" +

                        "You are no longer responsible for monitoring this asset.\n\n" +

                        "Regards,\nEAMS Team";

        emailService.sendEmail(to, subject, body);
    }

    //to the new user who has been reassigned an asset previously handled by someone else
    public void sendAssetReassignedToNewUser(String to, String name, Asset asset) {

        String subject = "Asset Assigned to You | EAMS";

        String body =
                "Hello " + name + ",\n\n" +

                        "An asset has been assigned to you by your manager.\n\n" +

                        "Asset Details:\n" +
                        "- Asset ID: " + asset.getId() + "\n" +
                        "- Type: " + capitalize(asset.getType()) + "\n" +
                        "- Location: " + asset.getLocation() + "\n\n" +

                        "Please take ownership and ensure proper monitoring.\n\n" +

                        "Regards,\nEAMS Team";

        emailService.sendEmail(to, subject, body);
    }




}
