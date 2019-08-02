package com.xiaohe.experienceUser.modules.member.service;

import org.springframework.data.domain.Page;
import com.xiaohe.experienceUser.base.IBaseServiceTwo;
import com.xiaohe.experienceUser.modules.member.entity.Member;
import com.xiaohe.experienceUser.modules.member.entity.QueryMember;

import java.util.List;

/**
 * <p>
 *  Member接口
 * </p>
 *
 * @author wzq
 * @since 2019-07-10
 */

public interface MemberService  extends IBaseServiceTwo<Member,Integer>{

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<Member> findAll(int page, int pageSize, QueryMember queryMember);

    /**
     * 根据Id查询list
     * @return
     */
    List<Member> findAllById(List<Integer> ids);


    List<Member> getAll(int min,int max,String userName,String companyName);

}


