package com.dot.ai.serviceimpl;

import com.dot.ai.dtos.request.EmailDetails;
import com.dot.ai.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("{spring.mail.username}")
    private String senderEmail;
    @Override
    public void sendEmailAlerts(EmailDetails emailDetails) {

        log.info("Entering mail sender method");

        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(senderEmail);
            simpleMailMessage.setTo(emailDetails.getRecipient());
            simpleMailMessage.setText(emailDetails.getMessageBody());
            simpleMailMessage.setSubject(emailDetails.getSubject());

            javaMailSender.send(simpleMailMessage);

            log.info("Mail sent successfully");
        }
        catch(MailException e){
            throw new RuntimeException(e);
        }
    }

}
