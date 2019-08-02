package com.xiaohe.mapshow.base;

import org.springframework.data.domain.Page;

import java.util.List;
/**
 * 2.0.4接口
 * @author gmq
 * @date 2018/12/1
 */
public interface IBaseServiceTwo<T,ID> {
    /**
     * 保存对象
     *
     * @param t 持久对象，或者对象集合
     * @throws Exception
     */
    T save(T t);


    /**
     * 通过list删除对象
     *
     * @param list
     */
    void deleteAll(List<T> list);

    /**
     * 删除对象
     *
     * @param t
     * @throws Exception
     */
    void delete(T t);

    /**
     * 通过id集合删除对象
     *
     * @param ids
     * @throws Exception
     */
    void deleteInBatch(List<ID> ids);

    /**
     * 通过id判断是否存在
     *
     * @param id
     * @throws Exception
     */
    boolean existsById(ID id);

    /**
     * 返回可用实体的数量
     *
     * @throws Exception
     */
    long count();

    /**
     * 通过id查询
     *
     * @param id
     * @return T
     * @throws Exception
     */
    T findById(ID id);

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return Page<T>
     */
    Page<T> findAll(int page, int pageSize);



}