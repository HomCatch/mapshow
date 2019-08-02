package com.xiaohe.mapshow.modules.register.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaohe.mapshow.modules.register.entity.EmailAuth;
import com.xiaohe.mapshow.modules.register.jpa.EmailAuthRepository;
import com.xiaohe.mapshow.modules.register.service.IEmailAuthService;
import com.xiaohe.mapshow.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Gmq
 * @since 2019-07-02 10:09
 **/
@Service
@DS("#session.tenantName")
public class IEmailAuthServiceImpl implements IEmailAuthService {

	@Autowired
	private EmailAuthRepository emailAuthRepository;
	/**
	 * 失效状态
	 * @param email 邮箱号
	 * @return 失效状态
	 */
	@Override
	public boolean failureState(String email) {
		EmailAuth byEmail = emailAuthRepository.findByEmail(email);
		long dateInterval = DateUtil.getDateInterval(byEmail.getDeadline(), new Date());
		return dateInterval > 0;
	}
	@Override
	@DS("#session.tenantName")
	public EmailAuth findByEmail(String email) {
		return emailAuthRepository.findByEmail(email);
	}


	@Override
	public EmailAuth save(EmailAuth emailAuth) {
		return emailAuthRepository.save(emailAuth);
	}

	@Override
	public void deleteAll(List<EmailAuth> list) {
		emailAuthRepository.deleteAll(list);
	}

	@Override
	public void delete(EmailAuth emailAuth) {
		emailAuthRepository.delete(emailAuth);
	}

	@Override
	public void deleteInBatch(List<Integer> integers) {

	}

	@Override
	public boolean existsById(Integer integer) {
		return emailAuthRepository.existsById(integer);
	}

	@Override
	public long count() {
		return emailAuthRepository.count();
	}

	@Override
	public EmailAuth findById(Integer integer) {
		Optional<EmailAuth> optional = emailAuthRepository.findById(integer);
		boolean present = optional.isPresent();
		return present ? optional.get() : null;
	}

	@Override
	public Page<EmailAuth> findAll(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		return emailAuthRepository.findAll(pageable);
	}

	@Override
	@DS("#session.tenantName")
	public boolean authState(String email, String authCode) {
		boolean flag = false;
		EmailAuth byEmail = findByEmail(email);
		if(byEmail==null){
			return flag;
		}
		String authCode2 = byEmail.getAuthCode();
		boolean failureState = failureState(email);
		if (StringUtils.isNotBlank(authCode) && authCode.equals(authCode2) && !failureState){
			return true;
		}
		return flag;
	}
}
