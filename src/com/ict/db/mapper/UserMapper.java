package com.ict.db.mapper;

import com.ict.db.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/05/16/9:13
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectUserByUserNameAndPassword(@Param("username") String username, @Param("password") String password);

    User seekUser(@Param("username") String username);
}