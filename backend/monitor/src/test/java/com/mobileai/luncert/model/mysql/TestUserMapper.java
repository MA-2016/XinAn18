package com.mobileai.luncert.model.mysql;


import com.mobileai.luncert.Application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserMapper {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testRegis() {
        userMapper.regis("xxx", "123");
    }

}