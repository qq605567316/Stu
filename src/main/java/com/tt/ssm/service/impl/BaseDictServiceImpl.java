package com.tt.ssm.service.impl;
import java.util.List;

import com.tt.ssm.dao.BaseDictDao;
import com.tt.ssm.entity.BaseDict;
import com.tt.ssm.service.BaseDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据字典Service接口实现类
 */
@Service("baseDictService")
public class BaseDictServiceImpl implements BaseDictService {
	
	@Autowired
	private BaseDictDao baseDictDao;
	
	//根据类别代码查询数据字典
	public List<BaseDict> findBaseDictByTypeCode(String typecode)
	{
		return baseDictDao.selectBaseDictByTypeCode(typecode);
	}
}
