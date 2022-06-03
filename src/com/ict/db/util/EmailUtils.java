package com.ict.db.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/06/02/15:40
 */
public class EmailUtils {
    public static void sendEmail(String emailAddress,String password) throws MessagingException {

        //创建一个properties对象
        Properties p = new Properties();
        //设置主机

        p.setProperty("mail.host", "smtp.qq.com");
        //设置传输协议
        p.setProperty("mail.transport.protocol", "smtp");
        //设置允许邮箱授权认证
        p.setProperty("mail.smtp.auth", "true");
        //邮箱授权的认证器
        //创建认证器对象
        Auth auth = new Auth();
        //获取Session会话对象
        Session session = Session.getDefaultInstance(p, auth);
        //获取连接
        Transport transport = session.getTransport();
        //连接服务器
        transport.connect("smtp.qq.com", "@qq.com", "");
        //创建邮箱对象
        MimeMessage message = new MimeMessage(session);
        //设置发件人地址
        message.setFrom(new InternetAddress("@qq.com"));
        //设置收件人地址
        //单发：
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
        //群发
        //InternetAddress[] address = {new InternetAddress(emailAddress)};
        //message.setRecipients(Message.RecipientType.TO, address);
        //设置邮箱标题
        message.setSubject("聊天室来信");
        //设置邮箱内容
        message.setContent("你的密码是"+password, "text/html;charset=utf-8");
        //发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        //关闭连接
        transport.close();
        System.out.println("发送完成");
    }
}
