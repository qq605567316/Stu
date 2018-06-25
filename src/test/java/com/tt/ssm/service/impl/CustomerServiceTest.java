package com.tt.ssm.service.impl;

import com.tt.ssm.BaseTest;
import com.tt.ssm.entity.Customer;
import com.tt.ssm.service.CustomerService;
import com.tt.ssm.utils.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class CustomerServiceTest extends BaseTest {
    @Autowired
    private CustomerService customerService;

    @Test
    public void testGetList(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("page",1);
        map.put("rows",10);
        map.put("custName","");
        map.put("custSource","");
        map.put("custIndustry","");
        map.put("custLevel","");
        Page<Customer> page = customerService.findCustomerList(map);
        System.out.println(page.getPage());
        System.out.println(page.getRows());
        System.out.println(page.getTotal());
        System.out.println(page.getSize());
    }
}
