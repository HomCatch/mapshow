package com.xiaohe.mapshow.modules.filterplan.service;

import com.xiaohe.mapshow.utils.Result;
import org.springframework.data.domain.Page;
import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.filterplan.entity.FilterPlan;
import com.xiaohe.mapshow.modules.filterplan.entity.QueryFilterPlan;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  FilterPlan接口
 * </p>
 *
 * @author gmq
 * @since 2019-03-29
 */

public interface FilterPlanService  extends IBaseServiceTwo<FilterPlan,Integer>{

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<FilterPlan> findAll(int page, int pageSize, QueryFilterPlan queryFilterPlan);

    /**
     * 根据Id查询list
     * @return
     */
    List<FilterPlan> findAllById(List<Integer> ids);


    @Transactional(rollbackFor = Exception.class)
    Result batchImport(MultipartFile file) throws Exception;

    void batchExport(List<Integer> ids) throws Exception;

    void downloadTemplate();
}


