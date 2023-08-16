package com.br.dinheiroemdia.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.br.dinheiroemdia.entities.LayoutEmailEntity;
import com.br.dinheiroemdia.exceptions.BusinessException;

@Service
public class EmailService {

	public void sendEmail(String destinationEmail, LayoutEmailEntity layoutEmailEntity) {

		String username = "sistemadinheiroemdia@gmail.com";
		String password = System.getenv("EMAIL_PASSWORD");
		
		if (password == null) {
		    throw new RuntimeException("Email password not set in environment.");
		}
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, password);
		    }
		});

		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(layoutEmailEntity.getSourceEmail()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinationEmail));

			message.setSubject(layoutEmailEntity.getSubject());
			message.setContent(layoutEmailEntity.getBody(), "text/html; charset=utf-8");

			Transport.send(message);

		} catch (MessagingException e) {
			throw new BusinessException(e.getMessage());
		}

	}

}
