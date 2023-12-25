package fr.wedidit.superplanning.superplanning.utils.mail;

import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
public class MailSender {

    private MailSender() {}

    public static void send(String mailTitle, String mailText, String mailDestination) throws MessagingException {
        String mailUsername = System.getProperty("email.username");
        String mailPassword = System.getProperty("email.password");

        // Paramètres SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Création d'une session avec authentification
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUsername, mailPassword);
            }
        });

        // Création du message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailUsername));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailDestination));
        message.setSubject(mailTitle);
        message.setText(mailText);

        // Envoi du message
        Transport.send(message);
    }

}
