package com.tt.ssm.service;
import com.tt.ssm.entity.Customer;
import com.tt.ssm.utils.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface CustomerService 
{
	// 查询客户列表
	public Page<Customer> findCustomerList(Map<String, Object> conditionMap);
	
	public int createCustomer(Customer customer);
	
	// 通过id查询客户
	public Customer getCustomerById(Integer id);
	
	// 更新客户
	public int updateCustomer(Customer customer);

    // 删除客户
    public int deleteCustomers(Integer[] ids);

	// 删除客户
	public int deleteCustomer(Integer id);

	public void importExcelInfo(InputStream in,MultipartFile file) throws Exception;

	public List<Map<String,String>> exportExcelInfo(Map<String,Object> exportmap) throws Exception;

}
