package com.tt.ssm.dao;

import com.tt.ssm.entity.Customer;

import java.util.List;



/**
 * Customer接口
 */
public interface CustomerDao
{
	// 客户列表
	public List<Customer> selectCustomerList(Customer customer);
	
	// 客户数
	public Integer selectCustomerListCount(Customer customer);
	
	// 创建客户
	public int createCustomer(Customer customer);
	
	// 通过id查询客户
	public Customer getCustomerById(Integer id);
	
	// 更新客户信息
	public int updateCustomer(Customer customer);
	
	// 删除客户
	int deleteCustomer(Integer id);

	/** 导入数据
	 * @param customerList
	 */
	public void insertInfoBatch(List<Customer> customerList);

    /**
     * 导出数据
     */
    public List<Customer> selectApartInfo(Customer customer);

	/**
	 *根据数据字典转化
	 */
	public String transform(String itemname);
}
