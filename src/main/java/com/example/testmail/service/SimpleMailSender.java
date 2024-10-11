package com.example.testmail.service;

import com.example.testmail.models.MailData;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleMailSender {

    private final JavaMailSender mailSender;

    // Основной метод для отправки письма
    public void sendMessage(final MailData mailData) throws MessagingException, IOException {
        // Проверяем, указаны ли получатели
        if (mailData.getTo() == null || mailData.getTo().isEmpty()) {
            throw new MessagingException("Recipient email address is not defined.");
        }

        // Создаем MimeMessage
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // Используем MimeMessageHelper для более простой работы с содержимым письма
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

        // Устанавливаем отправителя
        String setFrom = ((JavaMailSenderImpl) mailSender).getUsername();
        helper.setFrom(setFrom);

        // Устанавливаем получателей
        helper.setTo(mailData.getTo().toArray(new String[0]));

        // Устанавливаем тему письма
        helper.setSubject(mailData.getSubject());

        // Вызываем метод для сборки тела письма с изображениями
        buildMessage(mailData, helper);


        // Отправляем письмо
        mailSender.send(mimeMessage);
    }

    private void buildMessage(MailData mailData, MimeMessageHelper helper) throws MessagingException, IOException {
        // Генерируем HTML-тело письма, заменяя изображения на Base64
        String processedBody = processImagesInBody(mailData.getBody());

        // Устанавливаем HTML-тело письма

       // helper.setText(processedBody, true);  // true означает, что это HTML формат
        helper.setText(" ", processedBody);
        log.warn(processedBody);
    }

    private String processImagesInBody(String body) throws IOException {
        // Регулярное выражение для поиска тегов <img>
        Pattern imgPattern = Pattern.compile("<img[^>]+src=\"([^\"]+)\"[^>]*>");
        Matcher matcher = imgPattern.matcher(body);
        StringBuffer processedBody = new StringBuffer();

        // Обрабатываем каждый найденный тег <img>
        while (matcher.find()) {
            String imgPath = matcher.group(1); // Извлекаем путь к изображению

            try {
                // Читаем изображение и кодируем его в Base64
                byte[] imageBytes = Files.readAllBytes(Paths.get(imgPath));
                //String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                String base64Image = new String(Base64.getEncoder().encode(imageBytes), "UTF-8");
               // log.warn(base64Image);

                // Формируем новый тег <img> с Base64 изображением
                String base64Tag = String.format("<img src=\"data:image/jpg;base64,%s\" />", base64Image);

                // Заменяем старый тег <img> на новый с Base64 изображением
                matcher.appendReplacement(processedBody, base64Tag);
            } catch (IOException e) {
                log.error("Error loading image from path: {}", imgPath, e);
                matcher.appendReplacement(processedBody, imgPath); // Оставляем исходный путь, если ошибка
            }
        }

        // Добавляем оставшуюся часть HTML
        matcher.appendTail(processedBody);

        return processedBody.toString();
    }

    private byte[] downloadImage(String imageUrl) throws IOException {
        // Простая реализация скачивания изображения
        try (InputStream in = new URL(imageUrl).openStream()) {
            return in.readAllBytes();
        }
    }
}
