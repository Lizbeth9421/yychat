package com.ict.db.mapper;

import com.ict.db.domain.UserRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/05/19/13:48
 */
public interface UserRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRelation record);

    int insertSelective(UserRelation record);

    UserRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRelation record);

    int updateByPrimaryKey(UserRelation record);


    List<String> seekAllFriends(@Param("masterUser") String masterUser, @Param("relation") Integer relation);
}