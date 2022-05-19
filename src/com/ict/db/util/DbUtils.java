package com.ict.db.util;

import cn.hutool.core.util.ObjectUtil;
import com.ict.db.domain.User;
import com.ict.db.mapper.UserMapper;
import com.ict.db.mapper.UserRelationMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/05/16/10:45
 */
public class DbUtils {


    /**
     * 查找子此用户的所有好友
     *
     * @param userName
     * @param friendType
     * @return
     */
    public static String seekAllFriends(String userName, Integer friendType) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        UserRelationMapper userRelationMapper = sqlSession.getMapper(UserRelationMapper.class);
        StringBuilder allFriends = new StringBuilder();
        for (String friend : userRelationMapper.seekAllFriends(userName, friendType)) {
            allFriends.append(" ").append(friend);
        }
        sqlSession.close();
        return allFriends.toString();
    }


    /**
     * 判断用户是否存在
     *
     * @param username 用户名
     * @return
     */
    public static boolean seekUser(String username) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.seekUser(username);
        sqlSession.close();
        return ObjectUtil.isNull(user);
    }

    /**
     * 插入用户
     *
     * @param user
     */
    public static void insertUser(User user) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.insertSelective(user);
        sqlSession.commit();
        sqlSession.close();
    }


    /**
     * 登录方法
     * 查询用户名和密码是否匹配
     *
     * @param username
     * @param password
     * @return
     */
    public static boolean login(String username, String password) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectUserByUserNameAndPassword(username, password);
        sqlSession.close();
        return ObjectUtil.isNotNull(user);
    }


}
