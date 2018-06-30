package com.tt.ssm.dao;

import com.tt.ssm.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDictDaoTest extends BaseTest {
    @Autowired
    private BaseDictDao baseDictDao;

    @Test
    public void baseDictTest(){
        System.out.println(baseDictDao.selectBaseDictByTypeCode("001"));
    }
}
