package com.xiaohe.mapshow.modules.register.jpa;

import com.xiaohe.mapshow.modules.register.entity.Register;
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
 * @author gmq
 * @since 2019-04-22
 */

public interface RegisterRepository extends JpaRepository<Register, Integer> {

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<Register> findAll(Specification<Register> spec, Pageable pageable);

	/**
	 * 通过用户ID删除注册表信息
	 *
	 * @param userIds
	 */
	void deleteRegisterByUserIdIn(List<Integer> userIds);

}