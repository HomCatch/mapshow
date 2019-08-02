package com.xiaohe.mapshow.modules.dbinfo.service;

import com.baomidou.mybatisplus.service.IService;
import com.xiaohe.mapshow.modules.untils.PageUtils;
import com.xiaohe.mapshow.modules.untils.query.QueryVo;
import com.xiaohe.mapshow.modules.dbinfo.entity.DbInfo;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 *
 *  DbInfoService层
 *
 * @author gmq
 * @since 2019-07-10
 */
public interface DbInfoService extends IService<DbInfo> {

    PageUtils queryPage(QueryVo queryVo);
    /**
     * 查询未绑定公司的数据库
     * @return
     */
    List<DbInfo> queryUnbound();

    List<DbInfo> queryUnbound(String dsBase);
    /**
     * 查询已绑定公司的数据库
     * @return
     */
    List<DbInfo> queryBound(String dsBase);

    boolean exist(String dsBase,DbInfo dbInfo);

    /**
     * 批量插入dbinfo
     * @param dbInfos
     */
    void addDbInfos(String dsBase,@Param("list") List<DbInfo> dbInfos);

    List<DbInfo> selectList(String dsBase);
}

