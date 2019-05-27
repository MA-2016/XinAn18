package com.mobileai.luncert.model.mysql;


import com.mobileai.luncert.model.mysql.entity.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE account = #{account}")
    @Result(property = "id", column = "id", javaType = int.class)
    public int queryIdByAccount(@Param("account") String account);


    @Select("SELECT * FROM user WHERE account = #{account}")
    @Results({
        @Result(property = "id", column = "id", javaType = int.class),
        @Result(property = "account", column = "account", javaType = String.class),
        @Result(property = "password", column = "password", javaType = String.class),
        @Result(property = "isAdmin", column = "isAdmin", javaType = Boolean.class)
    })
    public User queryUserByAccount(@Param("account") String account);

    @Insert("INSERT INTO user(account, password) VALUES (#{account}, #{password})")
    public void regis(@Param("account") String account, @Param("password") String password);

    // isAdmin

    @Select("SELECT isAdmin FROM user WHERE account = #{account}")
    public boolean isAdmin(@Param("account") String account);
    
}