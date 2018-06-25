package com.tt.ssm.service.impl;

import com.tt.ssm.dao.UserDao;
import com.tt.ssm.entity.User;
import com.tt.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 用户Service接口实现类
 */
@Service("userService")
@Transactional
public class UserServiceImpl  implements UserService {

		// 注入UserDao
		@Autowired
		private UserDao userDao;
		
		// 通过账号和密码查询用户
		@Override
		public User findUser(String usercode, String password)
		{
			User user = userDao.findUser(usercode,password);

			if(user!=null&&user.getUser_state()==1){
				return user;
			}else {
				return null;
			}
		}
}

