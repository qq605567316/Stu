package com.tt.ssm.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.istack.internal.logging.Logger;
import com.tt.ssm.dao.CustomerDao;
import com.tt.ssm.entity.Customer;
import com.tt.ssm.service.CustomerService;
import com.tt.ssm.utils.Page;
import com.tt.ssm.utils.StringUtilsByMe;
import com.tt.ssm.utils.excel.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


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
        return customerDao.createCustomer(customer);
    }

    /**
     * 通过id查询客户
     */
    @Override
    public Customer getCustomerById(Integer id)
    {
        return customerDao.getCustomerById(id);
    }

    /**
     * 更新客户
     */
    @Override
    public int updateCustomer(Customer customer)
    {
        return customerDao.updateCustomer(customer);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Override
    public int deleteCustomers(Integer[] ids) {
        try{
            for (int i=0;i<ids.length;i++) {
                customerDao.deleteCustomer(ids[i]);
            }
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    /**
     * 删除客户
     */
    @Override
    public int deleteCustomer(Integer id)
    {
        return customerDao.deleteCustomer(id);
    }

    /**
     * 导入excel文件
     * @param in
     * @param file
     * @throws Exception
     */
    @Override
    public void importExcelInfo(InputStream in, MultipartFile file) throws Exception {
        List<List<Object>> listob = ExcelUtil.getBankListByExcel(in, file.getOriginalFilename());
        List<Customer> customerList = new ArrayList<Customer>();

        // 遍历listob数据，把数据放到List中
        for (int i = 0; i < listob.size(); i++) {
            List<Object> ob = listob.get(i);
            Customer customer = new Customer();

            // 通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载


            customer.setCust_name(String.valueOf(ob.get(1)));
            if(String.valueOf(ob.get(2)).equals("网络营销")){
                customer.setCust_source("7");
            }else {
                customer.setCust_source("6");
            }
            customer.setCust_industry(customerDao.transform(String.valueOf(ob.get(3))));
            customer.setCust_level(customerDao.transform(String.valueOf(ob.get(4))));
            customer.setCust_phone(String.valueOf(ob.get(5)));
            customer.setCust_mobile(String.valueOf(ob.get(6)));

            customerList.add(customer);

        }
        // 批量插入
        customerDao.insertInfoBatch(customerList);
    }

    /**
     * 导出
     * @param exportmap
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, String>> exportExcelInfo(Map<String,Object> exportmap) throws Exception {
        Customer exportCustomer = new Customer();

        if(exportmap.containsKey("exportName")&&StringUtilsByMe.
                isNotBlank((String)exportmap.get("exportName"))){
            exportCustomer.setCust_name((String)exportmap.get("exportName"));
        }
        if(exportmap.containsKey("exportSource")&&StringUtilsByMe.
                isNotBlank((String)exportmap.get("exportSource"))){
            exportCustomer.setCust_source((String)exportmap.get("exportSource"));
        }
        if(exportmap.containsKey("exportIndustry")&&StringUtilsByMe.
                isNotBlank((String)exportmap.get("exportIndustry"))){
            exportCustomer.setCust_industry((String)exportmap.get("exportIndustry"));
        }
        if(exportmap.containsKey("exportLevel")&&StringUtilsByMe.
                isNotBlank((String)exportmap.get("exportLevel"))){
            exportCustomer.setCust_level((String)exportmap.get("exportLevel"));
        }
        List<Customer> list = customerDao.selectApartInfo(exportCustomer);

        //处理取到的数据
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        for (Customer customer : list) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("编号", customer.getCust_id().toString());
            map.put("客户名称", customer.getCust_name());
            map.put("客户来源", customer.getCust_source());
            map.put("客户所属行业", customer.getCust_industry());
            map.put("客户级别", customer.getCust_level());
            map.put("固定电话", customer.getCust_phone());
            map.put("手机", customer.getCust_mobile());
            mapList.add(map);
       }
        return mapList;
    }

}
