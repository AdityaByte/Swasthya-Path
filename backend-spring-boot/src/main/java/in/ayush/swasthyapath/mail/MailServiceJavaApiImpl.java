package in.ayush.swasthyapath.mail;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("mailapi")
public class MailServiceJavaApiImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Override
    public boolean sendMail(String to, String subject, String text) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // enabled HTML flag as true.

            // Sending the mail.
            javaMailSender.send(message);

            return true;
        } catch (Exception e) {
            log.error("Failed to send email, {}", e.getMessage());
            return false;
        }
    }

}
