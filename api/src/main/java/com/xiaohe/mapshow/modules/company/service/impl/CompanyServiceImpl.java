package com.xiaohe.mapshow.modules.company.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xiaohe.mapshow.modules.company.dao.CompanyDao;
import com.xiaohe.mapshow.modules.company.entity.Company;
import com.xiaohe.mapshow.modules.company.service.CompanyService;
import com.xiaohe.mapshow.modules.member.entity.Member;
import com.xiaohe.mapshow.modules.member.service.MemberService;
import com.xiaohe.mapshow.modules.untils.PageUtils;
import com.xiaohe.mapshow.modules.untils.query.QueryToWrapper;
import com.xiaohe.mapshow.modules.untils.query.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CompanyServiceImpl层
 *
 * @author gmq
 * @since 2019-07-10
 */
@Service("companyService")
@DS("#session.dsBase")
public class CompanyServiceImpl extends ServiceImpl<CompanyDao, Company> implements CompanyService {

	@Autowired
	private MemberService memberService;
	@Autowired
	private CompanyDao companyDao;

	@Override
	public PageUtils queryPage(QueryVo queryVo) {
		EntityWrapper<Company> entityWrapper = new EntityWrapper<>();
		//查询前预处理
		QueryToWrapper.convert(entityWrapper, queryVo);
		Page<Company> page = this.selectPage(
				new Page<>(queryVo.getPage(), queryVo.getPageSize()),
				entityWrapper
		);
		page.setTotal(this.selectCount(null));
		return new PageUtils(page);
	}

	/**
	 * 注册新公司 员工表新增一条记录（超管）
	 *
	 * @param company
	 * @return
	 */
	@Override
	public boolean registerCompany(Company company, Member member) throws Exception {
		boolean flag0 = false;
		try {
			int insert = companyDao.addCompany(company);
			//员工表新增该公司超管
			member.setCompanyId(company.getId());
			boolean flag2 = memberService.insert(member);
			if (insert>0 && flag2) {
				flag0 = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return flag0;
	}

	@Override
	public Company findCompany(Company company) {
		return baseMapper.selectOne(company);
	}

	@Override
	public int selectCompanyCount(String username) {
		Wrapper<Company> wrapper = new EntityWrapper<Company>()
				.eq("username", username);
		Integer integer = baseMapper.selectCount(wrapper);
		return integer == null ? 0 : integer;
	}
}
