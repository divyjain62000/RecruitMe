package com.recruitme.service.mail;

import com.recruitme.enums.ApplicationProperties;
import com.recruitme.enums.mail.EmailImageConstant;
import com.recruitme.enums.mail.MailTemplate;
import com.recruitme.service.domain.drive.dto.DriveDTO;
import com.recruitme.service.mail.dto.ConfirmationTokenDTO;
import com.recruitme.service.mail.dto.MailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * This service is used to send and manage email related things
 *
 * @author DIVY JAIN
 * @version 1.0
 * @since Sept 12, 2021 7:50:00PM
 */
@Service("emailSenderService")
@EnableAsync
@Slf4j
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private MailProperties  mailProperties;

    @Async
    public void sendVerificationEmail(MailDTO mailDTO, ConfirmationTokenDTO confirmationTokenDTO) {
        String templateName=MailTemplate.VERIFICATION_EMAIL_TEMPLATE.getMailTemplate();
        System.out.println("Java MAil Sender: "+javaMailSender);
        MimeMessage message = javaMailSender.createMimeMessage();
        Context context=new Context();
        Map<String,Object> emailMap=mailDTO.getEmailMap();
        for(String key:emailMap.keySet()) {
            context.setVariable(key,emailMap.get(key));
        }
        context.setVariable("url",ApplicationProperties.BASE_URL_BACKEND.getApplicationProperties()+"/api/verify?token="+confirmationTokenDTO.getToken());
        String body=templateEngine.process(templateName, context);
        try
        {
            MimeMessageHelper mimeMessageHelper =new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(MailTemplate.MAIL_FROM.getMailTemplate());
            String recipientEmails[]=new String[mailDTO.getTo().size()];
            recipientEmails=mailDTO.getTo().toArray(recipientEmails);
            mimeMessageHelper.setTo(recipientEmails);
            mimeMessageHelper.setSubject(mailDTO.getSubject());
            mimeMessageHelper.setText(body,true);
            javaMailSender.send(message);
        }catch(MessagingException messagingException) {
            System.out.print(messagingException.getMessage());
        }
    }

    @Async
    public void sendDriveNotificationEmail(MailDTO mailDTO, DriveDTO driveDTO) {
        String templateName=MailTemplate.DRIVE_NOTIFICATION_EMAIL_TEMPLATE.getMailTemplate();
        System.out.println("Java MAil Sender: "+javaMailSender);
        MimeMessage message = javaMailSender.createMimeMessage();
        Context context=new Context();
        context.setVariable("company",driveDTO.getCompany());
        context.setVariable("lastDateToApply",driveDTO.getLastDateToApply());
        context.setVariable("url",ApplicationProperties.BASE_URL_FRONTENT.getApplicationProperties());
        String body=templateEngine.process(templateName, context);
        try
        {
            MimeMessageHelper mimeMessageHelper =new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(MailTemplate.MAIL_FROM.getMailTemplate());
            int size=mailDTO.getTo().size();
            log.debug("Students Size: "+size);
            String recipients[]=new String[size];
            for(int i=0;i<10;i++) {
                recipients[i]=mailDTO.getTo().get(i);
                mimeMessageHelper.setTo(recipients[i]);
                mimeMessageHelper.setSubject(mailDTO.getSubject());
                mimeMessageHelper.setText(body,true);
                javaMailSender.send(message);
            }

        }catch(MessagingException messagingException) {
            System.out.print(messagingException.getMessage());
        }
    }

    @Async
    public void sendWelcomeEmail(MailDTO mailDTO) {
        String templateName=MailTemplate.WELCOME_EMAIL_TEMPLATE.getMailTemplate();
        System.out.println("Java MAil Sender: "+javaMailSender);
        MimeMessage message = javaMailSender.createMimeMessage();
        Context context=new Context();
        Map<String,Object> emailMap=mailDTO.getEmailMap();
        for(String key:emailMap.keySet()) {
            context.setVariable(key,emailMap.get(key));
        }
        setImagesInEmail(context);
        context.setVariable("url", ApplicationProperties.BASE_URL_FRONTENT.getApplicationProperties());
        String body=templateEngine.process(templateName, context);
        try
        {
            MimeMessageHelper mimeMessageHelper =new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(MailTemplate.MAIL_FROM.getMailTemplate());
            String recipientEmails[]=new String[mailDTO.getTo().size()];
            recipientEmails=mailDTO.getTo().toArray(recipientEmails);
            mimeMessageHelper.setTo(recipientEmails);
            mimeMessageHelper.setSubject(mailDTO.getSubject());
            mimeMessageHelper.setText(body,true);
            javaMailSender.send(message);
        }catch(MessagingException messagingException) {
            System.out.print(messagingException.getMessage());
        }
    }

    private void setImagesInEmail(Context context) {
        context.setVariable("facebookImg", EmailImageConstant.FACEBOOK_IMG);
        context.setVariable("linkedinImg",EmailImageConstant.LINKED_IMG);
        context.setVariable("youtubeImg",EmailImageConstant.YOUTUBE_IMG);
        context.setVariable("instagramImg",EmailImageConstant.INSTAGRAM_IMG);
        context.setVariable("twitterImg",EmailImageConstant.TWITTER_IMG);
        context.setVariable("emailLogo",EmailImageConstant.EMAIL_LOGO);
    }


    @Async
    public void sendResetPasswordEmail(MailDTO mailDTO, ConfirmationTokenDTO confirmationTokenDTO) {
        String templateName=MailTemplate.RESET_PASSWORD_EMAIL_TEMPLATE.getMailTemplate();
        System.out.println("Java MAil Sender: "+javaMailSender);
        MimeMessage message = javaMailSender.createMimeMessage();
        Context context=new Context();
        Map<String,Object> emailMap=mailDTO.getEmailMap();
        for(String key:emailMap.keySet()) {
            context.setVariable(key,emailMap.get(key));
        }
        context.setVariable("url",ApplicationProperties.BASE_URL_FRONTENT.getApplicationProperties()+"/reset-password?token="+confirmationTokenDTO.getToken());
        String body=templateEngine.process(templateName, context);
        try
        {
            MimeMessageHelper mimeMessageHelper =new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(MailTemplate.MAIL_FROM.getMailTemplate());
            String recipientEmails[]=new String[mailDTO.getTo().size()];
            recipientEmails=mailDTO.getTo().toArray(recipientEmails);
            mimeMessageHelper.setTo(recipientEmails);
            mimeMessageHelper.setSubject(mailDTO.getSubject());
            mimeMessageHelper.setText(body,true);
            javaMailSender.send(message);
        }catch(MessagingException messagingException) {
            System.out.print(messagingException.getMessage());
        }
    }

}
