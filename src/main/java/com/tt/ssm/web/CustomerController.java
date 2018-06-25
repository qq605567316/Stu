package com.tt.ssm.web;

import com.tt.ssm.entity.Customer;
import com.tt.ssm.service.CustomerService;
import com.tt.ssm.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户管理控制器类
 */
@Controller
@RequestMapping("/customer")
public class CustomerController
{
    @Autowired
    private CustomerService customerService;

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

        model.addAttribute("custName", custName);
        model.addAttribute("custSource", custSource);
        model.addAttribute("custIndustry", custIndustry);
        model.addAttribute("custLevel", custLevel);

        return "customer";
    }

}
