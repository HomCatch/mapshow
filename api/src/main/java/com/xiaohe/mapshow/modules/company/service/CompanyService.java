package com.xiaohe.mapshow.modules.company.service;

import com.baomidou.mybatisplus.service.IService;
import com.xiaohe.mapshow.modules.member.entity.Member;
import com.xiaohe.mapshow.modules.untils.PageUtils;
import com.xiaohe.mapshow.modules.untils.query.QueryVo;
import com.xiaohe.mapshow.modules.company.entity.Company;

import java.util.Map;

/**
 *
 *  CompanyService层
 *
 * @author gmq
 * @since 2019-07-10
 */
public interface CompanyService extends IService<Company> {

    PageUtils queryPage(QueryVo queryVo);

    /**
     * 注册新公司 员工表新增一条记录（超管）
     * @param company
     * @return
     */
    boolean registerCompany(Company company,Member member) throws Exception;

    Company  findCompany(Company company);

    int selectCompanyCount(String username);


}

