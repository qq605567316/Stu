package com.tt.ssm.web;

import com.tt.ssm.entity.BaseDict;
import com.tt.ssm.entity.Customer;
import com.tt.ssm.entity.User;
import com.tt.ssm.service.BaseDictService;
import com.tt.ssm.service.CustomerService;
import com.tt.ssm.utils.Page;
import com.tt.ssm.utils.excel.ViewExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;

/**
 * 客户管理控制器类
 */
@Controller
@RequestMapping("/customer")
public class CustomerController
{
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BaseDictService baseDictService;
    // 客户来源
//    @Value("${customer.form.type}")
    private String FROM_TYPE = "002";
    // 客户所属行业
//    @Value("${customer.industry.type}")
    private String INDUSTRY_TYPE = "001";
    // 客户级别
//    @Value("${customer.level.type}")
    private String LEVEL_TYPE = "006";
    @RequestMapping(value = "/list")
    private String list(@RequestParam(defaultValue="1")Integer page,
                        @RequestParam(defaultValue="10")Integer rows,
                        String custName, String custSource, String custIndustry,
                        String custLevel, Model model){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("page",page);
        map.put("rows",rows);
        map.put("custName",custName);
        map.put("custSource",custSource);
        map.put("custIndustry",custIndustry);
        map.put("custLevel",custLevel);
        Page<Customer> customerPage = customerService.findCustomerList(map);
        model.addAttribute("page",customerPage);

        // 客户来源
        List<BaseDict> fromType = baseDictService
                .findBaseDictByTypeCode(FROM_TYPE);
        // 客户所属行业
        List<BaseDict> industryType = baseDictService
                .findBaseDictByTypeCode(INDUSTRY_TYPE);
        // 客户级别
        List<BaseDict> levelType = baseDictService
                .findBaseDictByTypeCode(LEVEL_TYPE);
        // 添加参数
        model.addAttribute("fromType", fromType);
        model.addAttribute("industryType", industryType);
        model.addAttribute("levelType", levelType);
        model.addAttribute("custName", custName);
        model.addAttribute("custSource", custSource);
        model.addAttribute("custIndustry", custIndustry);
        model.addAttribute("custLevel", custLevel);

        return "customer";
    }

    /**
     * 创建客户
     */
    @RequestMapping("/create")
    @ResponseBody
    public String customerCreate(Customer customer,HttpSession session) {
        // 获取Session中的当前用户信息
        User user = (User) session.getAttribute("USER_SESSION");
        // 将当前用户id存储在客户对象中
        customer.setCust_create_id(user.getUser_id());
        // 创建Date对象
        Date date = new Date();
        // 得到一个Timestamp格式的时间，存入mysql中的时间格式“yyyy/MM/dd HH:mm:ss”
        Timestamp timeStamp = new Timestamp(date.getTime());
        customer.setCust_createtime(timeStamp);
        // 执行Service层中的创建方法，返回的是受影响的行数
        int rows = customerService.createCustomer(customer);
        if(rows > 0){
            return "OK";
        }else{
            return "FAIL";
        }
    }

    /**
     * 通过id获取客户信息
     */
    @RequestMapping("/getCustomerById")
    @ResponseBody
    public Customer getCustomerById(Integer id) {
        Customer customer = customerService.getCustomerById(id);
        return customer;
    }

    /**
     * 更新客户
     */
    @RequestMapping("/update")
    @ResponseBody
    public String customerUpdate(Customer customer) {
        int rows = customerService.updateCustomer(customer);
        if(rows > 0){
            return "OK";
        }else{
            return "FAIL";
        }
    }

    /**
     * 批量删除客户
     */
    @RequestMapping(value = "/deleteall")
    @ResponseBody
    public String customerDeletes(Integer[] ids) {
        int rows = customerService.deleteCustomers(ids);

        if(rows > 0){
            return "OK";
        }else{
            return "FAIL";
        }
    }

    /**
     * 删除客户
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String customerDelete(Integer id) {
        int rows = customerService.deleteCustomer(id);
        if(rows > 0){
            return "OK";
        }else{
            return "FAIL";
        }
    }

    /**
     * 导入
     */
    @RequestMapping("/import")
    public String importExcel(HttpServletRequest request, Model model) throws Exception {
        // 获取上传的文件
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
        MultipartFile file = multipart.getFile("upfile");

        //开始导入
        InputStream in = file.getInputStream();

        // 数据导入
        customerService.importExcelInfo(in, file);
        return "redirect:/customer/list";
    }

    /**
     * 导出
     */
    @RequestMapping("/export")
    public ModelAndView export(String exportName, String exportSource, String exportIndustry,
                               String exportLevel,ModelMap map) throws Exception{
        Map<String,Object> exportmap = new HashMap<String, Object>();
        exportmap.put("exportName",exportName);
        exportmap.put("exportSource",exportSource);
        exportmap.put("exportIndustry",exportIndustry);
        exportmap.put("exportLevel",exportLevel);
        List<Map<String,String>> list = customerService.exportExcelInfo(exportmap);
        String[] titles = {"编号","客户名称","客户来源","客户所属行业","客户级别","固定电话","手机"};
        ViewExcel excel = new ViewExcel(titles);
        map.put("excelList",list);
        return new ModelAndView(excel,map);
    }
}
