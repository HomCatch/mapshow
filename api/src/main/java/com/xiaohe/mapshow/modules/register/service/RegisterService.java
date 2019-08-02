package com.xiaohe.mapshow.modules.register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.register.entity.Register;
import com.xiaohe.mapshow.modules.register.entity.QueryRegister;
import com.xiaohe.mapshow.modules.register.jpa.RegisterRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;
/**
 * <p>
 *  Register接口
 * </p>
 *
 * @author gmq
 * @since 2019-04-22
 */

public interface RegisterService  extends IBaseServiceTwo<Register,Integer>{

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<Register> findAll(int page, int pageSize, QueryRegister queryRegister);

    /**
     * 根据Id查询list
     * @return
     */
    List<Register> findAllById(List<Integer> ids);
    /**
     * 通过用户ID删除注册表信息
     *
     * @param userIds
     */
    void deleteRegisterByUserIdIn(List<Integer> userIds);


}


