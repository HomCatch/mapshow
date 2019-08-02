package com.xiaohe.experienceUser.log.service;

import com.xiaohe.experienceUser.log.jpa.OperationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 * wzq
 */
@Service("operationLogService")
public class OperationLogService {
    @Autowired
    private OperationLogRepository operationLogRepository;
    @Transactional(rollbackFor = { RuntimeException.class })
    public void add(String userName, String description,String param,String creatDate) {
        operationLogRepository.add(userName,description,param,creatDate);
    }

}
