package com.ict.db.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

//认证器类
public class Auth extends Authenticator {

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("@qq.com","");
    }
}
