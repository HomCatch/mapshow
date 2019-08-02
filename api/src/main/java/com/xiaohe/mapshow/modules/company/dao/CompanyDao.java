package com.xiaohe.mapshow.modules.company.dao;

import com.xiaohe.mapshow.modules.company.entity.Company;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 *
 *  CompanyDao层
 *
 * @author gmq
 * @since 2019-07-10
 */
public interface CompanyDao extends BaseMapper<Company> {
	/**
	 * 新增公司 返回主键
	 * @param company
	 * @return
	 */
	Integer addCompany(Company company);
}
