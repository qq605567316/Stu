package com.tt.ssm.dao;

import com.tt.ssm.BaseTest;
import com.tt.ssm.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoTest extends BaseTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testUserLogin(){

        String usercode = "m0001";
        String password = "123";
        User user = userDao.findUser(usercode,password);
        System.out.println(user);
    }

}
