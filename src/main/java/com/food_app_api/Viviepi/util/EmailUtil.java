package com.food_app_api.Viviepi.util;

import com.food_app_api.Viviepi.repositories.IUserRepository;
import com.food_app_api.Viviepi.util.StringsUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class EmailUtil {
    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;
    private final StringsUtil stringsUtil;

    @Autowired
    public EmailUtil(JavaMailSender javaMailSender, MailProperties mailProperties, StringsUtil stringsUtil) {
        this.javaMailSender = javaMailSender;
        this.mailProperties = mailProperties;
        this.stringsUtil = stringsUtil;
    }


    public void sendVerificationEmail(String email, String verifyCode) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email xác nhận";
        String senderName = "Cổng dịch vụ đăng ký tài khoản người dùng Viviepi";
        String mailContent =
                "<img align=\"center\" border=\"0\" hspace=\"0\"" +
                        " src=\"https://res.cloudinary.com/dpxakd3vt/image/upload/v1711374384/ic_launcher_background_app_ci9tdm.png\"" +
                        " style=\"max-width:70px;margin-left:auto;display:block;margin-right:auto\" vspace=\"0\" width=\"70px\" data-bit=\"iit\">"+
                        " <h1 style=\"text-align:center\">Xin chào "+ stringsUtil.getUserNameFormDomain(email)+" </h1>" +
                        "<p align=\"center\">"+"<a style=\"padding:10px;width:104px;height:16px;display:block;margin:8;" +
                        "text-decoration:none;border:1px solid #ef5b25;text-align:center;font-size:12px;font-style:normal;font-weight:600;font-family:'Open Sans',sans-serif;color:#fff;background:#ef5b25;border-radius:5px;line-height:16px\">" +
                        verifyCode+"</a>" +"</p>"+
                        "<p align=\"center\"> Xin cảm ơn ! Cổng dịch vụ đăng ký tài khoản người dùng Viviepi";
        MimeMessage messages = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(messages);
        messageHelper.setFrom(mailProperties.getUsername(), senderName);
        messageHelper.setTo(email);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(messages);
    }

    public void sendResetPassword(String email, String verifyCode) throws MessagingException, UnsupportedEncodingException{
        String subject = "Đặt lại mật khẩu";
        String senderName = "Cổng dịch vụ đăng ký tài khoản người dùng Viviepi";
        String mailContent =
                "<img align=\"center\" border=\"0\" hspace=\"0\"" +
                        " src=\"https://res.cloudinary.com/dpxakd3vt/image/upload/v1711374384/ic_launcher_background_app_ci9tdm.png\"" +
                        " style=\"max-width:70px;margin-left:auto;display:block;margin-right:auto\" vspace=\"0\" width=\"70px\" data-bit=\"iit\">"+
                        " <h1 style=\"text-align:center\">Xin chào "+ stringsUtil.getUserNameFormDomain(email)+" </h1>" +
                        "<p align=\"center\">"+"<a style=\"padding:10px;width:104px;height:16px;display:block;margin:8;" +
                        "text-decoration:none;border:1px solid #ef5b25;text-align:center;font-size:12px;font-style:normal;font-weight:600;font-family:'Open Sans',sans-serif;color:#fff;background:#ef5b25;border-radius:5px;line-height:16px\">" +
                        verifyCode+"</a>" +"</p>"+
                        "<p align=\"center\"> Xin cảm ơn ! Cổng dịch vụ đăng ký tài khoản người dùng Viviepi";
        MimeMessage messager = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(messager);
        messageHelper.setFrom(mailProperties.getUsername(), senderName);
        messageHelper.setTo(email);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(messager);
    }

}
