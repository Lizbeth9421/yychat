package com.ict.db.util;

import cn.hutool.core.util.ObjectUtil;
import com.ict.db.domain.User;
import com.ict.db.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/05/16/10:45
 */
public class DbUtils {


    /**
     * 判断用户是否存在
     *
     * @param username 用户名
     * @return
     */
    public static boolean seekUser(String username) {
        SqlSession sqlsession = MyBatisUtil.getSqlSession();
        UserMapper usermapper = sqlsession.getMapper(UserMapper.class);
        User user = usermapper.seekUser(username);
        sqlsession.close();
        return ObjectUtil.isNull(user);
    }

    /**
     * 插入用户
     *
     * @param user
     */
    public static void insertUser(User user) {
        SqlSession sqlsession = MyBatisUtil.getSqlSession();
        UserMapper usermapper = sqlsession.getMapper(UserMapper.class);
        usermapper.insertSelective(user);
        sqlsession.commit();
        sqlsession.close();
    }


    public static boolean login(String username,String password){
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectUserByUserNameAndPassword(username, password);
        sqlSession.close();
        return ObjectUtil.isNotNull(user);
    }


}
