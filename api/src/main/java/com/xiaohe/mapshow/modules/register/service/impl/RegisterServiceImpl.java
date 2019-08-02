package com.xiaohe.mapshow.modules.register.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaohe.mapshow.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.modules.register.service.RegisterService;
import com.xiaohe.mapshow.modules.register.entity.Register;
import com.xiaohe.mapshow.modules.register.entity.QueryRegister;
import com.xiaohe.mapshow.modules.register.jpa.RegisterRepository;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Register服务类
 * </p>
 *
 * @author gmq
 * @since 2019-04-22
 */

@Service
@DS("#session.tenantName")
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private RegisterRepository registerRepository;

	/**
	 * 保存对象
	 *
	 * @param register 对象
	 *                 持久对象，或者对象集合
	 */
	@Override
	public Register save(Register register) {
		return registerRepository.save(register);
	}

	/**
	 * 删除对象
	 *
	 * @param register 对象
	 */
	@Override
	public void delete(Register register) {
		registerRepository.delete(register);
	}

	@Override
	public void deleteAll(List<Register> list) {
		registerRepository.deleteAll(list);
	}

	/**
	 * 通过id集合删除对象
	 *
	 * @param ids
	 */
	@Override
	public void deleteInBatch(List<Integer> ids) {
		registerRepository.deleteInBatch(registerRepository.findAllById(ids));
	}

	/**
	 * 通过id判断是否存在
	 *
	 * @param id
	 */
	@Override
	public boolean existsById(Integer id) {
		return registerRepository.existsById(id);
	}

	/**
	 * 返回可用实体的数量
	 */
	@Override
	public long count() {
		return registerRepository.count();
	}

	/**
	 * 通过id查询
	 *
	 * @param id id
	 * @return Register对象
	 */
	@Override
	public Register findById(Integer id) {
		Optional<Register> optional = registerRepository.findById(id);
		boolean present = optional.isPresent();
		return present ? optional.get() : null;
	}

	/**
	 * 分页查询
	 * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
	 *
	 * @param page     页面
	 * @param pageSize 页面大小
	 * @return Page<Register>对象
	 */
	@Override
	public Page<Register> findAll(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		return registerRepository.findAll(pageable);
	}

	@Override
	public Page<Register> findAll(int page, int pageSize, QueryRegister queryRegister) {
		Pageable pageable =  PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		//查询条件构造
		Specification<Register> spec = new Specification<Register>() {
			@Override
			public Predicate toPredicate(Root<Register> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (StringUtils.isNotBlank(queryRegister.getPhone())) {
					predicate.getExpressions().add(cb.like(root.get("phone").as(String.class), "%" + queryRegister.getPhone() + "%"));
				}
				return predicate;
			}

		};
		return registerRepository.findAll(spec, pageable);
	}

	/**
	 * 根据Id查询list
	 *
	 * @param ids id集合
	 * @return list
	 */
	@Override
	public List<Register> findAllById(List<Integer> ids) {
		return registerRepository.findAllById(ids);
	}

	@Override
	public void deleteRegisterByUserIdIn(List<Integer> userIds) {
		registerRepository.deleteRegisterByUserIdIn(userIds);
	}
}


