package com.xiaohe.experienceUser.modules.member.jpa;

import com.xiaohe.experienceUser.modules.member.entity.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * jpa 接口
 *
 * @author wzq
 * @since 2019-07-10
 */

@Transactional(rollbackFor = Exception.class)
public interface MemberRepository extends JpaRepository<Member, Integer> {

    /**
     * 按条件查询方案
     *
     * @param spec     spec
     * @param pageable 分页
     * @return page
     */
    Page<Member> findAll(Specification<Member> spec, Pageable pageable);


    @Query(value = "select m.id as id,m.company_id as company_id,m.username as username,m.create_time as create_time,c.company_name as company_name from member m LEFT JOIN company c on m.company_id=c.id where IF ( ?3 = null or ?3 = '',1,m.username LIKE %?3%) and IF ( ?4 = null or ?4 = '',1,c.company_name LIKE %?4%) ORDER BY m.id limit ?1,?2 ;", nativeQuery = true)
    List<Member> getAll(@Param("min") int min, @Param("max") int max, @Param("userName") String userName, @Param("companyName") String companyName);


}