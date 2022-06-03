package test;

import com.ict.db.util.EmailUtils;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/04/16/22:30
 */
public class test {

    @Test
    public void test() throws MessagingException {
        EmailUtils.sendEmail("3512348186@qq.com", "1111");
    }
}

