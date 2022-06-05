package com.ict.db.util;

import cn.hutool.core.util.ObjectUtil;
import com.ict.db.domain.Message;
import com.ict.db.domain.User;
import com.ict.db.domain.UserRelation;
import com.ict.db.mapper.MessageMapper;
import com.ict.db.mapper.UserMapper;
import com.ict.db.mapper.UserRelationMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/05/16/10:45
 */
public class DbUtils {


    /**
     * 插入聊天信息
     *
     * @param message
     */
    public static void insertMessage(Message message) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);
        messageMapper.insertSelective(message);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 插入好友关系
     *
     * @param relation
     */
    public static void insertUserRelation(UserRelation relation) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        UserRelationMapper userRelationMapper = sqlSession.getMapper(UserRelationMapper.class);
        userRelationMapper.insertSelective(relation);
        sqlSession.commit();
        sqlSession.close();
    }


    /**
     * 删除好友关系
     *
     * @param sender
     * @param deleteFriend
     * @param friendType
     */
    public static void deleteUserRelation(String sender, String deleteFriend, Integer friendType) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        UserRelationMapper userRelationMapper = sqlSession.getMapper(UserRelationMapper.class);
        userRelationMapper.deleteUserRelationByUser(sender, deleteFriend, friendType);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 查询新好友是不是已经是好友了
     *
     * @param sender
     * @param newFriend
     * @param friendType
     * @return true 存在 false 不存在
     */
    public static boolean seekFriendIsExit(String sender, String newFriend, int friendType) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        UserRelationMapper userRelationMapper = sqlSession.getMapper(UserRelationMapper.class);
        UserRelation userRelation = userRelationMapper.seekFriendIsExit(sender, newFriend, friendType);
        sqlSession.close();
        return ObjectUtil.isNotNull(userRelation);
    }


    /**
     * 查找此用户的所有好友
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
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return
     */
    public static User selectUserByName(String username) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.seekUser(username);
        sqlSession.close();
        return user;
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
     * 更新用户
     *
     * @param user
     */
    public static void updateUser(User user) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.updateByPrimaryKeySelective(user);
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
