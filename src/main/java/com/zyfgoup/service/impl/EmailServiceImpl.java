package com.zyfgoup.service.impl;

import com.zyfgoup.common.exception.SystemException;
import com.zyfgoup.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * @Author Zyfgoup
 * @Date 2020/6/26 14:24
 * @Description
 */
@Service
public class EmailServiceImpl implements EmailService {
    //template模板引擎
    @Autowired
    private TemplateEngine templateEngine;



    //从application.properties配置文件中注入发送者的邮件地址
    @Value("${spring.mail.username}")
    private String fromEmail;

    //注入spring发送邮件的对象
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean sendStartMail(String to, String subject, Map<String,Object> content) throws SystemException {
        try {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
        messageHelper.setFrom(fromEmail);// 发送人的邮箱
        messageHelper.setTo(to);//发给谁  对方邮箱
        messageHelper.setSubject(subject); //标题
        //使用模板thymeleaf
        //Context是导这个包import org.thymeleaf.context.Context;
        Context context = new Context();
        //定义模板数据
        context.setVariables(content);
        //获取thymeleaf的html模板
        String emailContent = templateEngine.process("conferenceStartEmail",context); //指定模板路径
        messageHelper.setText(emailContent,true);

            javaMailSender.send(mimeMessage);  		//执行发送
        } catch (MessagingException e) {

           throw new SystemException("邮件发送失败");
        }
        return true;
    }

    @Override
    public boolean sendAuditMail(String to, String subject, Map<String, Object> content) throws SystemException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
            messageHelper.setFrom(fromEmail);// 发送人的邮箱
            messageHelper.setTo(to);//发给谁  对方邮箱
            messageHelper.setSubject(subject); //标题
            //使用模板thymeleaf
            //Context是导这个包import org.thymeleaf.context.Context;
            Context context = new Context();
            //定义模板数据
            context.setVariables(content);
            //获取thymeleaf的html模板  不要加"/” 不然在jar包发布下 会报错
            String emailContent = templateEngine.process("auditResultEmail",context); //指定模板路径
            messageHelper.setText(emailContent,true);

            javaMailSender.send(mimeMessage);  		//执行发送
        } catch (MessagingException e) {

            throw new SystemException("邮件发送失败");
        }
        return true;
    }
}
