package com.xiaohe.mapshow.modules.portraitstatic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.portraitstatic.entity.PortraitStatic;
import com.xiaohe.mapshow.modules.portraitstatic.entity.QueryPortraitStatic;
import com.xiaohe.mapshow.modules.portraitstatic.jpa.PortraitStaticRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;
/**
 * <p>
 *  PortraitStatic接口
 * </p>
 *
 * @author wzq
 * @since 2019-06-04
 */

public interface PortraitStaticService  extends IBaseServiceTwo<PortraitStatic,Integer>{

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<PortraitStatic> findAll(int page, int pageSize, QueryPortraitStatic queryPortraitStatic);

    Page<PortraitStatic> findAll(int page, int pageSize,String dbName);

    /**
     * 根据Id查询list
     * @return
     */
    List<PortraitStatic> findAllById(List<Integer> ids);

    void save(PortraitStatic portraitStatic,String dbName);
}


