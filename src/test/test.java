package test;

import com.ict.db.mapper.UserMapper;
import com.ict.db.util.DbUtils;
import com.ict.db.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/04/16/22:30
 */
public class test {

    @Test
    public void test(){
        String friends = DbUtils.seekAllFriends("mai", 1);
        System.out.println(friends);
    }
}

