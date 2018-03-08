package com.MyMall.dao;

import org.apache.ibatis.annotations.Param;

import com.MyMall.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    int checkUserName(String username);
    
    User checkLogin(@Param("username") String username, @Param("password") String password);
    
    int checkEmail(String email);
    
    String getQuestion(String username);
    
    int checkAnwser (@Param("username")String username, @Param("password")String question, @Param("answer")String answer);
    
    int updatePswByUsername(@Param("username")String username,@Param("password")String password);
    
    int checkPassword(@Param("id")int id,@Param("password")String password);
    
    int updatePswById(@Param("id")int id,@Param("password")String password);
}