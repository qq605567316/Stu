package com.tt.ssm.dao;

import com.tt.ssm.BaseTest;
import com.tt.ssm.entity.Customer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerDaoTest extends BaseTest {
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testCustomerList(){
        Customer customer = new Customer();
        customer.setStart(0);
        customer.setRows(10);
        List<Customer> list = customerDao.selectCustomerList(customer);
        for (Customer c:list) {
            System.out.println(c.getCust_name());
        }
    }
}
