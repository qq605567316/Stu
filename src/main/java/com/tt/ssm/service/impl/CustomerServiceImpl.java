package com.tt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import com.sun.istack.internal.logging.Logger;
import com.tt.ssm.dao.CustomerDao;
import com.tt.ssm.entity.Customer;
import com.tt.ssm.service.CustomerService;
import com.tt.ssm.utils.Page;
import com.tt.ssm.utils.StringUtilsByMe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 客户管理
 */
@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService
{
    /**
     * 打印日志
     */
    private static Logger logger = Logger.getLogger(CustomerServiceImpl.class);

    /**
     * 声明DAO属性并注入
     */
    @Autowired
    private CustomerDao customerDao;

    // 客户列表
    public Page<Customer> findCustomerList(Map<String, Object> conditionMap)
    {
        Integer page = (Integer) conditionMap.get("page");
        Integer rows = (Integer) conditionMap.get("rows");
        Customer customer = new Customer();

        if(conditionMap.containsKey("custName")&&StringUtilsByMe.
                isNotBlank((String)conditionMap.get("custName"))){
            customer.setCust_name((String)conditionMap.get("custName"));
        }
        if(conditionMap.containsKey("custSource")&&StringUtilsByMe.
                isNotBlank((String)conditionMap.get("custSource"))){
            customer.setCust_source((String)conditionMap.get("custSource"));
        }
        if(conditionMap.containsKey("custIndustry")&&StringUtilsByMe.
                isNotBlank((String)conditionMap.get("custIndustry"))){
            customer.setCust_industry((String)conditionMap.get("custIndustry"));
        }
        if(conditionMap.containsKey("custLevel")&&StringUtilsByMe.
                isNotBlank((String)conditionMap.get("custLevel"))){
            customer.setCust_level((String)conditionMap.get("custLevel"));
        }

        customer.setStart((page-1)*rows);
        customer.setRows(rows);
        List<Customer> customerList = customerDao.selectCustomerList(customer);
        Integer count = customerDao.selectCustomerListCount(customer);
        Page<Customer> customerPage = new Page<Customer>();

        customerPage.setPage(page);
        customerPage.setSize(rows);
        customerPage.setTotal(count);
        customerPage.setRows(customerList);
        return customerPage;
    }

    /**
     * 创建客户
     */
    @Override
    public int createCustomer(Customer customer)
    {
        return 0;
    }

    /**
     * 通过id查询客户
     */
    @Override
    public Customer getCustomerById(Integer id)
    {

        return null;
    }

    /**
     * 更新客户
     */
    @Override
    public int updateCustomer(Customer customer)
    {
        return 0;
    }

    /**
     * 删除客户
     */
    @Override
    public int deleteCustomer(Integer id)
    {
        return 0;
    }

}
