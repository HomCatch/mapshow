package com.xiaohe.mapshow.modules.register.jpa;

import com.xiaohe.mapshow.modules.register.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Integer> {
	/**
	 * 根据邮箱号查询
	 * @param email 邮箱号
	 * @return EmailAuth
	 */
	EmailAuth findByEmail(String email);
}
