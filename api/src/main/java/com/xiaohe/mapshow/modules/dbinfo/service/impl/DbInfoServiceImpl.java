package com.xiaohe.mapshow.modules.dbinfo.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaohe.mapshow.modules.untils.PageUtils;
import com.xiaohe.mapshow.modules.untils.query.QueryVo;
import com.xiaohe.mapshow.modules.untils.query.QueryToWrapper;
import com.xiaohe.mapshow.modules.dbinfo.service.DbInfoService;
import com.xiaohe.mapshow.modules.dbinfo.entity.DbInfo;
import com.xiaohe.mapshow.modules.dbinfo.dao.DbInfoDao;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 *  DbInfoServiceImpl层
 *
 * @author gmq
 * @since 2019-07-10
 */
@Service("dbInfoService")
@DS("#session.dsBase")
public class DbInfoServiceImpl extends ServiceImpl<DbInfoDao, DbInfo> implements DbInfoService {

	@Autowired
	private DbInfoDao dbInfoDao;

    @Override
    public PageUtils queryPage(QueryVo queryVo) {
        EntityWrapper<DbInfo> entityWrapper = new EntityWrapper<>();
        //查询前预处理
        QueryToWrapper.convert(entityWrapper, queryVo);
        Page<DbInfo> page = this.selectPage(
                new Page<>(queryVo.getPage(), queryVo.getPageSize()),
                entityWrapper
        );
        page.setTotal(this.selectCount(null));
        return new PageUtils(page);
    }

	@Override
	public List<DbInfo> queryUnbound() {
		return dbInfoDao.queryUnbound() ;
	}
	@Override
	@DS("#dsBase")
	public List<DbInfo> queryUnbound(String dsBase) {
		return dbInfoDao.queryUnbound() ;
	}
	@Override
	@DS("#dsBase")
	public List<DbInfo> queryBound(String dsBase) {
		return dbInfoDao.queryBound() ;
	}

	@Override
	@DS("#dsBase")
	public boolean exist(String dsBase,DbInfo dbInfo) {
		DbInfo dbInfo1 = baseMapper.selectOne(dbInfo);
		return dbInfo1 != null;

	}

	@Override
	@DS("#dsBase")
	public void addDbInfos(String dsBase, List<DbInfo> dbInfos) {
		dbInfoDao.addDbInfos(dbInfos);
	}

	@Override
	@DS("#dsBase")
	public List<DbInfo> selectList(String dsBase) {
		return selectList(new EntityWrapper<>());
	}
}
