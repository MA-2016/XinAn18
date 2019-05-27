package com.mobileai.luncert.service;


import java.security.NoSuchAlgorithmException;

import com.mobileai.luncert.model.mysql.UserMapper;
import com.mobileai.luncert.model.mysql.entity.User;
import com.mobileai.luncert.utils.CipherHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagement {

    @Autowired
    private UserMapper userMapper;

    public boolean avaliable(String account) throws NoSuchAlgorithmException {
        account = CipherHelper.hashcode(account);
        return userMapper.queryUserByAccount(account) == null;
    }

    public User validate(String account, String password) throws NoSuchAlgorithmException {
        account = CipherHelper.hashcode(account);
        password = CipherHelper.hashcode(password); 
        User user = userMapper.queryUserByAccount(account);
        if (user != null && user.getPassword().compareTo(password) == 0) return user;
        else return null;
    }

    public void regis(String account, String password) throws NoSuchAlgorithmException {
        account = CipherHelper.hashcode(account);
        password = CipherHelper.hashcode(password);
        userMapper.regis(account, password);
    }

}