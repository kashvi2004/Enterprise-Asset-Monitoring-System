package com.enterprise.eams.common.email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEmailService {

    @Autowired
    private EmailService emailService;

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

        emailService.sendHtmlEmail(to, subject, html);
    }
}
