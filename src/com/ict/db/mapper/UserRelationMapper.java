package com.ict.db.mapper;

import com.ict.db.domain.UserRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/05/19/13:48
 */
@Mapper
public interface UserRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRelation record);

    int insertSelective(UserRelation record);

    UserRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRelation record);

    int updateByPrimaryKey(UserRelation record);


    List<String> seekAllFriends(@Param("masterUser") String masterUser, @Param("relation") Integer relation);

    /**
     * 查询该好友是否存在
     *
     * @param sender
     * @param newFriend
     * @param friendType
     * @return
     */
    UserRelation seekFriendIsExit(@Param("sender") String sender, @Param("newFriend") String newFriend, @Param(
            "friendType") Integer friendType);


    /**
     * 删除
     * @param sender
     * @param deleteFriend
     * @param friendType
     */
    void deleteUserRelationByUser(@Param("sender") String sender, @Param("deleteFriend") String deleteFriend, @Param(
            "friendType") Integer friendType);
}