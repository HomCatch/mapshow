package com.xiaohe.mapshow.modules.register.jpa;

import com.xiaohe.mapshow.modules.register.entity.VerifiCodeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface VerifiCodeRepository extends JpaRepository<VerifiCodeRecord, Integer> {
	/**
	 * 根据手机号查询记录
	 * @param phone 手机号
	 * @return 验证码记录
	 */
	VerifiCodeRecord findByPhone(String phone);
}
