package com.example.testmail;

import com.example.testmail.models.MailData;
import com.example.testmail.service.SimpleMailSender;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class TestmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestmailApplication.class, args);
	}
	@Bean
	public CommandLineRunner run(SimpleMailSender simpleMailSender) {
		return args -> {
			// Подготовим данные для отправки письма
			Set<String> recipients = new HashSet<>();
			recipients.add("vlad.mishikhin@mail.ru");  // Заменить на реальный email получателя

			String bodyWithImage = "<html><body><h1>Hello</h1><img src=\"src/main/resources/static/b9752983-9d03-4306-a3d7-2acbb36e825a.jpg\" /></body></html>";

			MailData mailData = MailData.builder()
					.to(recipients)
					.subject("Test Email with Base64 Image")
					.body(bodyWithImage)
					.build();

			// Отправка сообщения
			simpleMailSender.sendMessage(mailData);

			System.out.println("Email sent successfully!");
		};
	}

}
