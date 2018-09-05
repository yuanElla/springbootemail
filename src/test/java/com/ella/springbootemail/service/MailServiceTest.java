package com.ella.springbootemail.service;

import com.ella.springbootemail.mail.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;

/**
 * @author ella
 * @date 2018/9/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Resource
    MailService mailService;

    @Resource
    TemplateEngine templateEngine;

    String toAddress = "12345667@qq.com";//收件人的邮箱

    @Test
    public void testSimpleMailSend(){
        mailService.sendSimpleMail(toAddress, "测试邮件", "Hello World! Hello Java!");
    }

    @Test
    public void testHtmlMail() throws MessagingException {
        String context = "<html>\n"+
                "<body>\n"+
                "<h2> Hello Java, 这是一封html邮件！</h2>\n"
                +"</body>\n"
                + "</html>";
        mailService.sendHtmlMail(toAddress, "测试发送html邮件", context);
    }

    @Test
    public void testAttachementMail() throws MessagingException {
        String filePath = "/Users/ella/Downloads/rocketmq-all-4.3.0-source-release.zip";
        mailService.sendAttachmentsMail(toAddress, "测试发送附件邮件", "这是一封带附件的邮件", filePath);
    }

    @Test
    public void testInlineResourceMail() throws MessagingException {
        String srcPath = "/Users/ella/Desktop/123.png";
        String srcId = "ella001";
        String content =  "<html>\n"+
                "<body>\n"+
                "<img src=\'cid:" + srcId + "\'/>"
                +"</body>\n"
                + "</html>";
        mailService.sendInlineResourceMail(toAddress, "这是一封带图片的邮件", content, srcPath, srcId);
    }

    @Test
    public void testTemplateMail() throws MessagingException {
        Context context = new Context();
        context.setVariable("id", "002");

        String emailContent = templateEngine.process("emailTemplate", context);//第一个参数是模板名称(不包括后缀)
        mailService.sendHtmlMail(toAddress, "测试发送模板邮件", emailContent);
    }
}
