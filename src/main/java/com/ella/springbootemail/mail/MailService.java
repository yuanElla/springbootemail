package com.ella.springbootemail.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author ella
 * @date 2018/9/4
 */
@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送简单的文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param text 内容
     */
    public void sendSimpleMail(String to, String subject, String text){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        simpleMailMessage.setFrom(from);

        javaMailSender.send(simpleMailMessage);
    }

    /**
     * 发送html邮件
     * @param to 收件人
     * @param subject 主题
     * @param text 内容
     * @throws MessagingException
     */
    public void sendHtmlMail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);//true表示为html邮件
        helper.setFrom(from);
        javaMailSender.send(message);
    }

    /**
     * 发送附件邮件
     * @param to
     * @param subject
     * @param text
     * @param filePath
     */
    public void sendAttachmentsMail(String to, String subject, String text, String filePath) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = file.getFilename();
        helper.addAttachment(fileName, file);//可以利用该方法添加多个附件
        javaMailSender.send(message);
    }

    /**
     * 发送图片邮件
     * @param to
     * @param subject
     * @param text
     * @param srcPath
     * @param srcId
     * @throws MessagingException
     */
    public void sendInlineResourceMail(String to, String subject, String text, String srcPath, String srcId) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        FileSystemResource file = new FileSystemResource(new File(srcPath));
        helper.addInline(srcId, file);//可以利用该方法增加多张图片
        javaMailSender.send(message);
    }
}
