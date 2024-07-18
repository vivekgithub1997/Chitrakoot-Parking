package com.chitrakoot.parking.utillity;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String toMail;

	public String sendMailWithoutAttachment(String fromMail, String subjectMail, String messageBody) {
		// TODO Auto-generated method stub

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo(fromMail);
		mailMessage.setFrom(toMail);
		mailMessage.setSubject(subjectMail);
		mailMessage.setText(messageBody);

		javaMailSender.send(mailMessage);
		// mailSender.send(mailMessage);
		return "MAIL SEND SUCCESSFULLY.....!!";
	}

	public String sendMailWithAttachment(String fromMail, String subjectMail, String messageBody, String attachment)
			throws IOException {
		// TODO Auto-generated method stub

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setTo(fromMail);
			mimeMessageHelper.setFrom(toMail);

			mimeMessageHelper.setSubject(subjectMail);
			mimeMessageHelper.setText(messageBody);

			FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));

			mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
			// mimeMessageHelper.addAttachment(messageMail, attchment.getBytes());

			javaMailSender.send(mimeMessage);

			return "MAIL SEND SUCCESSFULLY...!!";
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failed to send email.";
		}
	}

}
