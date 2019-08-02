package com.xiaohe.mapshow.modules.changelist.jpa;

import com.xiaohe.mapshow.modules.changelist.entity.ChangeList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 *  jpa 接口
 *
 * @author hzh
 * @since 2019-04-08
 */

@Transactional(rollbackFor = Exception.class)
public interface ChangeListRepository extends JpaRepository<ChangeList, Integer> {

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<ChangeList> findAll(Specification<ChangeList> spec, Pageable pageable);

    @Query(value = "select * from change_list",nativeQuery = true)
    List<ChangeList> getAll();
}