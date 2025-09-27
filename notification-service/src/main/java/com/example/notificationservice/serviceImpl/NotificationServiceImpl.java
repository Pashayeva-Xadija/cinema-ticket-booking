package com.example.notificationservice.serviceImpl;

import com.example.notificationservice.dto.EmailRequest;
import com.example.notificationservice.dto.SmsRequest;
import com.example.notificationservice.service.NotificationService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;



    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendEmail(EmailRequest req) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();


            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(req.getTo());
            helper.setSubject(req.getSubject());


            String payload = "bookingId=" + req.getBookingId() +
                    ";seat=" + req.getSeatNumber();


            BitMatrix matrix = new MultiFormatWriter()
                    .encode(payload, BarcodeFormat.QR_CODE, 250, 250);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] qrBytes = baos.toByteArray();


            String html = """
                    <h3>Booking confirmed</h3>
                    <p><b>Booking #</b>%s &nbsp; <b>Seat:</b> %s</p>
                    <p>Show this QR at the gate:</p>
                    <img src="cid:qr" alt="QR" />
                    """.formatted(
                    req.getBookingId() == null ? "-" : req.getBookingId(),
                    req.getSeatNumber() == null ? "-" : req.getSeatNumber()
            );

            helper.setText(html, true);
            helper.addInline("qr", new ByteArrayResource(qrBytes), "image/png");

            mailSender.send(msg);
            log.info("✅ Email sent to {} (bookingId={}, seat={})",
                    req.getTo(), req.getBookingId(), req.getSeatNumber());

        } catch (Exception e) {
            log.error("❌ Mail send failed", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    public void sendSms(SmsRequest req) {
        log.info("📱 Fake SMS sent to {} with message: {}", req.getPhone(), req.getMessage());
    }
}
