package com.xiaohe.mapshow.modules.untils.query;


import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xiaohe.mapshow.config.aspect.DataFilterAspect;
import com.xiaohe.mapshow.config.validate.XException;
import com.xiaohe.mapshow.modules.sys.entity.SysUserEntity;
import com.xiaohe.mapshow.modules.sys.shiro.ShiroUtils;
import com.xiaohe.mapshow.modules.untils.query.Condition.Operator;
import com.xiaohe.mapshow.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 组合查询条件方法
 *
 * @author hzh
 * @date 2018-12-29
 */
public final class QueryToWrapper {

    private static Pattern pattern = Pattern.compile("\\d+(,\\d+)*");

    private static volatile QueryToWrapper instance;

    private QueryToWrapper() {
    }

    public static QueryToWrapper getInstance() {
        if (instance == null) {
            synchronized (QueryToWrapper.class) {
                if (instance == null) {
                    instance = new QueryToWrapper();
                }
            }
        }
        return instance;
    }

    /**
     * 查询前预处理
     *
     * @param wrapper 查询对象
     * @param queryVo 查询条件
     */
    public static void convert(final Wrapper wrapper, final QueryVo queryVo) {
        //组合查询条件
        QueryToWrapper.parseCondition(wrapper, queryVo);
        //排序
        if (queryVo.getSortVo() != null) {
            QueryToWrapper.parseSort(wrapper, queryVo.getSortVo());
        }
    }

    /**
     * 查询前预处理
     */
    public static void convert2(final Predicate predicate, final Root root, final CriteriaBuilder cb, final QueryVo queryVo) {
        //组合查询条件
        QueryToWrapper.parseCondition2(predicate, root, cb, queryVo);

    }

    /**
     * 创建查询条件
     *
     * @param wrapper 查询对象
     * @param queryVo 查询条件
     */
    public static void parseCondition(final Wrapper wrapper, final QueryVo queryVo) {
        Map<String, Object> params = queryVo.getParams();
        Map<String, Object> operators = queryVo.getOperators();
        if (!(CollectionUtils.isEmpty(operators) && CollectionUtils.isEmpty(params))) {
            for (String key : operators.keySet()) {
                Object param = params.get(key);
                Object operator = operators.get(key);
                String paramStr = param.toString();
                boolean notEmpty = StringUtils.isNotEmpty(paramStr);
                if (Operator.isNull.getSymbol().equals(operator)) {
                    wrapper.isNull(paramStr);
                } else if (Operator.isNotNull.getSymbol().equals(operator)) {
                    wrapper.isNotNull(paramStr);
                } else if (Operator.le.getSymbol().equals(operator)) {
                    wrapper.le(key, paramStr);
                } else if (Operator.ne.getSymbol().equals(operator)) {
                    wrapper.ne(key, paramStr);
                } else if (Operator.eq.getSymbol().equals(operator)) {
                    wrapper.eq(key, paramStr);
                } else if (Operator.gt.getSymbol().equals(operator)) {
                    wrapper.gt(key, paramStr);
                } else if (Operator.ge.getSymbol().equals(operator)) {
                    wrapper.ge(notEmpty, key, paramStr);
                } else if (Operator.in.getSymbol().equals(operator)) {
                    wrapper.in(key, paramStr);
                } else if (Operator.notIn.getSymbol().equals(operator)) {
                    wrapper.notIn(key, paramStr);
                } else if (Operator.between.getSymbol().equals(operator)) {
                    String[] split = paramStr.split(",");
                    wrapper.between(key, split[0], split[1]);
                } else if (Operator.like.getSymbol().equals(operator)) {
                    wrapper.like(notEmpty, key, "%" + paramStr + "%");
                } else if (Operator.notLike.getSymbol().equals(operator)) {
                    wrapper.notLike(notEmpty, key, paramStr);
                }
            }
        }
    }

    /**
     * 创建查询条件
     *
     * @param predicate 查询对象
     * @param root      查询对象
     * @param cb        查询对象
     * @param queryVo   查询条件
     */
    public static Predicate parseCondition2(final Predicate predicate, final Root root, final CriteriaBuilder cb, final QueryVo queryVo) {
        Map<String, Object> params = queryVo.getParams();
        Map<String, Object> operators = queryVo.getOperators();
        if (!(CollectionUtils.isEmpty(operators) && CollectionUtils.isEmpty(params))) {
            for (String key : operators.keySet()) {
                Object param = params.get(key);
                Object operator = operators.get(key);
                String paramStr = param.toString();
                boolean notEmpty = StringUtils.isNotEmpty(paramStr);
                List<Expression<Boolean>> list = predicate.getExpressions();
                if (Operator.isNull.getSymbol().equals(operator)) {
                    list.add(cb.isNull(root.get(key)));
                } else if (Operator.isNotNull.getSymbol().equals(operator)) {
                    list.add(cb.isNotNull(root.get(key)));
                } else if (Operator.le.getSymbol().equals(operator)) {
                    list.add(cb.le(root.get(key), Integer.parseInt(paramStr)));
                } else if (Operator.ne.getSymbol().equals(operator)) {
                    list.add(cb.notEqual(root.get(key), Integer.parseInt(paramStr)));
                } else if (Operator.eq.getSymbol().equals(operator)) {
                    list.add(cb.equal(root.get(key), paramStr));
                } else if (Operator.gt.getSymbol().equals(operator)) {
                    list.add(cb.greaterThan(root.get(key), Integer.parseInt(paramStr)));
                } else if (Operator.ge.getSymbol().equals(operator)) {
                    list.add(cb.greaterThanOrEqualTo(root.get(key), Integer.parseInt(paramStr)));
                } else if (Operator.in.getSymbol().equals(operator)) {
                    CriteriaBuilder.In<Object> in = cb.in(root.get(key));
                    if (StringUtils.isNotBlank(paramStr) && pattern.matcher(paramStr).find()) {
                        List<Integer> integers = StringUtil.stringToIntegerList(paramStr);
                        for (Integer integer : integers) {
                            in.value(integer);
                        }
                    } else {
                        String[] strings = paramStr.split(",");
                        for (String s : strings) {
                            in.value(s);
                        }
                    }
                } else if (Operator.notIn.getSymbol().equals(operator)) {
//                    未实现
                } else if (Operator.between.getSymbol().equals(operator)) {
                    String[] split = paramStr.split(",");
                    list.add(cb.between(root.get(key), split[0], split[1]));
                } else if (Operator.like.getSymbol().equals(operator)) {
                    list.add(cb.like(root.get(key), "%" + paramStr + "%"));
                } else if (Operator.notLike.getSymbol().equals(operator)) {
                    list.add(cb.notLike(root.get(key), "%" + paramStr + "%"));
                }
                Set<Attribute> attributes = root.getModel().getDeclaredAttributes();
                for (Attribute attribute : attributes) {
                    if ("deptId".equals(attribute.getName())) {
                        CriteriaBuilder.In<Integer> in = cb.in(root.get("deptId"));
                        SysUserEntity user = ShiroUtils.getUserEntity();
                        if (user.getUserId() != 1) {
                            String apendSql = DataFilterAspect.apendSql;
                            try {
                                if (StringUtils.isNotBlank(apendSql)) {
                                    //处理为List<Integer>
                                    for (Integer integer : DataFilterAspect.sqlToDeptList(apendSql)) {
                                        in.value(integer);
                                    }
                                    predicate.getExpressions().add(cb.and(in));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                throw new XException("过滤sql时处理异常");
                            }
                        }
                    }
                }


            }
        }
        return predicate;
    }

    /**
     * 排序
     *
     * @param wrapper 组合查询对象
     * @param sortVo  排序对象
     */
    public static void parseSort(Wrapper wrapper, SortVo sortVo) {
        String[] orders = sortVo.getOrders();
        if (orders.length > 0) {
            if (sortVo.getDirection().equals(Direction.DESC.name())) {
                for (String order : orders) {
                    wrapper.orderBy(order, false);
                }
            } else if (sortVo.getDirection().equals(Direction.ASC.name())) {
                for (String order : orders) {
                    wrapper.orderBy(order, false);
                }
            }
        }
    }

    /**
     * 排序
     *
     * @param queryVo 条件对象
     */
    public static Pageable parseSort2(QueryVo queryVo) {
        Pageable pageable = null;
        SortVo sortVo = queryVo.getSortVo();
        if (null != sortVo) {
            String[] orders = sortVo.getOrders();
            if (orders.length > 0) {
                if (sortVo.getDirection().equals(Direction.DESC.name())) {
                    pageable = PageRequest.of(queryVo.getPage(), queryVo.getPageSize(), Sort.Direction.DESC, orders);
                } else if (sortVo.getDirection().equals(Direction.ASC.name())) {
                    pageable = PageRequest.of(queryVo.getPage(), queryVo.getPageSize(), Sort.Direction.ASC, orders);
                }
            }
        }
        return pageable == null ? PageRequest.of(queryVo.getPage(), queryVo.getPageSize(), Sort.Direction.DESC, "id") : pageable;
    }

}
