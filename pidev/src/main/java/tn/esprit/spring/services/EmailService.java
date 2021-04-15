package tn.esprit.spring.services;

	import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
	public interface EmailService {
		public void sendEmail();
	}
