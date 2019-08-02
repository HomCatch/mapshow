package com.xiaohe.mapshow.modules.portraitstatic.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.modules.portraitstatic.service.PortraitStaticService;
import com.xiaohe.mapshow.modules.portraitstatic.entity.PortraitStatic;
import com.xiaohe.mapshow.modules.portraitstatic.entity.QueryPortraitStatic;
import com.xiaohe.mapshow.modules.portraitstatic.jpa.PortraitStaticRepository;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * PortraitStatic服务类
 * </p>
 *
 * @author wzq
 * @since 2019-06-04
 */

@Service
@DS("#session.tenantName")
public class PortraitStaticServiceImpl implements PortraitStaticService {

    @Autowired
    private PortraitStaticRepository portraitStaticRepository;

    /**
     * 保存对象
     *
     * @param portraitStatic 对象
     *                       持久对象，或者对象集合
     */
    @Override
    public PortraitStatic save(PortraitStatic portraitStatic) {
        return portraitStaticRepository.save(portraitStatic);
    }

    /**
     * 删除对象
     *
     * @param portraitStatic 对象
     */
    @Override
    public void delete(PortraitStatic portraitStatic) {
        portraitStaticRepository.delete(portraitStatic);
    }

    @Override
    public void deleteAll(List<PortraitStatic> list) {
        portraitStaticRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        portraitStaticRepository.deleteInBatch(portraitStaticRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return portraitStaticRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return portraitStaticRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return PortraitStatic对象
     */
    @Override
    public PortraitStatic findById(Integer id) {
        Optional<PortraitStatic> optional = portraitStaticRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<PortraitStatic>对象
     */
    @Override
    public Page<PortraitStatic> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "date");
        return portraitStaticRepository.findAll(pageable);
    }

    @Override
    @DS("#dbName")
    public Page<PortraitStatic> findAll(int page, int pageSize,String dbName) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "date");
        return portraitStaticRepository.findAll(pageable);
    }

    @Override
    public Page<PortraitStatic> findAll(int page, int pageSize, QueryPortraitStatic queryPortraitStatic) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "date");
        //查询条件构造
        Specification<PortraitStatic> spec = new Specification<PortraitStatic>() {
            @Override
            public Predicate toPredicate(Root<PortraitStatic> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (queryPortraitStatic.getUserId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("userId").as(Integer.class), queryPortraitStatic.getUserId()));
                }

                if (queryPortraitStatic.getMale() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("male").as(Integer.class), queryPortraitStatic.getMale()));
                }

                if (queryPortraitStatic.getFemale() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("female").as(Integer.class), queryPortraitStatic.getFemale()));
                }

                if (queryPortraitStatic.getChildCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("childCount").as(Integer.class), queryPortraitStatic.getChildCount()));
                }

                if (queryPortraitStatic.getTeenCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("teenCount").as(Integer.class), queryPortraitStatic.getTeenCount()));
                }

                if (queryPortraitStatic.getYoungCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("youngCount").as(Integer.class), queryPortraitStatic.getYoungCount()));
                }

                if (queryPortraitStatic.getMiddleCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("middleCount").as(Integer.class), queryPortraitStatic.getMiddleCount()));
                }

                if (queryPortraitStatic.getOldCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("oldCount").as(Integer.class), queryPortraitStatic.getOldCount()));
                }

                if (queryPortraitStatic.getHuawei() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("Huawei").as(Integer.class), queryPortraitStatic.getHuawei()));
                }

                if (queryPortraitStatic.getApple() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("Apple").as(Integer.class), queryPortraitStatic.getApple()));
                }

                if (queryPortraitStatic.getXiaomi() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("Xiaomi").as(Integer.class), queryPortraitStatic.getXiaomi()));
                }

                if (queryPortraitStatic.getSamSong() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("SamSong").as(Integer.class), queryPortraitStatic.getSamSong()));
                }

                if (queryPortraitStatic.getOther() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("other").as(Integer.class), queryPortraitStatic.getOther()));
                }

                if (queryPortraitStatic.getGuangDong() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("GuangDong").as(Integer.class), queryPortraitStatic.getGuangDong()));
                }

                if (queryPortraitStatic.getJiangSu() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("JiangSu").as(Integer.class), queryPortraitStatic.getJiangSu()));
                }

                if (queryPortraitStatic.getShanDong() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("ShanDong").as(Integer.class), queryPortraitStatic.getShanDong()));
                }

                if (queryPortraitStatic.getZheJiang() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("ZheJiang").as(Integer.class), queryPortraitStatic.getZheJiang()));
                }

                if (queryPortraitStatic.getLiaoNing() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("LiaoNing").as(Integer.class), queryPortraitStatic.getLiaoNing()));
                }

                if (queryPortraitStatic.getSiChuan() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("SiChuan").as(Integer.class), queryPortraitStatic.getSiChuan()));
                }

                if (queryPortraitStatic.getHuBei() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("HuBei").as(Integer.class), queryPortraitStatic.getHuBei()));
                }

                if (queryPortraitStatic.getHuNan() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("HuNan").as(Integer.class), queryPortraitStatic.getHuNan()));
                }

                if (queryPortraitStatic.getHeBei() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("HeBei").as(Integer.class), queryPortraitStatic.getHeBei()));
                }

                if (queryPortraitStatic.getHeNan() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("HeNan").as(Integer.class), queryPortraitStatic.getHeNan()));
                }

                if (queryPortraitStatic.getFuJian() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("FuJian").as(Integer.class), queryPortraitStatic.getFuJian()));
                }

                if (queryPortraitStatic.getGuiZhou() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("GuiZhou").as(Integer.class), queryPortraitStatic.getGuiZhou()));
                }

                if (queryPortraitStatic.getYunNan() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("YunNan").as(Integer.class), queryPortraitStatic.getYunNan()));
                }

                if (queryPortraitStatic.getXiZan() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("XiZan").as(Integer.class), queryPortraitStatic.getXiZan()));
                }

                return predicate;
            }

        };
        return portraitStaticRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<PortraitStatic> findAllById(List<Integer> ids) {
        return portraitStaticRepository.findAllById(ids);
    }

    @Override
    @DS("#dbName")
    public void save(PortraitStatic portraitStatic,String dbName){
        portraitStaticRepository.save(portraitStatic);
    }
}


