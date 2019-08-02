package com.xiaohe.experienceUser.modules.member.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.experienceUser.modules.member.service.MemberService;
import com.xiaohe.experienceUser.modules.member.entity.Member;
import com.xiaohe.experienceUser.modules.member.entity.QueryMember;
import com.xiaohe.experienceUser.modules.member.jpa.MemberRepository;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Member服务类
 * </p>
 *
 * @author wzq
 * @since 2019-07-10
 */

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    /**
     * 保存对象
     *
     * @param member 对象
     *               持久对象，或者对象集合
     */
    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    /**
     * 删除对象
     *
     * @param member 对象
     */
    @Override
    public void delete(Member member) {
        memberRepository.delete(member);
    }

    @Override
    public void deleteAll(List<Member> list) {
        memberRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        memberRepository.deleteInBatch(memberRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return memberRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return memberRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return Member对象
     */
    @Override
    public Member findById(Integer id) {
        Optional<Member> optional = memberRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<Member>对象
     */
    @Override
    public Page<Member> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return memberRepository.findAll(pageable);
    }

    @Override
    public Page<Member> findAll(int page, int pageSize, QueryMember queryMember) {
        //过滤自己的广告
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<Member> spec = new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (queryMember.getCompanyId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("companyId").as(Integer.class), queryMember.getCompanyId()));
                }

                if (StringUtils.isNotBlank(queryMember.getUsername())) {
                    predicate.getExpressions().add(cb.like(root.get("username").as(String.class), "%" + queryMember.getUsername() + "%"));
                }
                if (StringUtils.isNotBlank(queryMember.getStartTime())) {
                    predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createTime").as(String.class), queryMember.getStartTime()));
                }
                if (StringUtils.isNotBlank(queryMember.getEndTime())) {
                    predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createTime").as(String.class), queryMember.getEndTime()));
                }
                return predicate;
            }

        };
        return memberRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<Member> findAllById(List<Integer> ids) {
        return memberRepository.findAllById(ids);
    }

    @Override
    public List<Member> getAll(int min, int max,String userName,String companyName) {
        List<Member> list = memberRepository.getAll(min, max,userName,companyName);
        return list;
    }
}


