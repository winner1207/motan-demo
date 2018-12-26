package com.wyt.motan.chatbusiness.api;

import com.alibaba.fastjson.JSON;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import com.wyt.motan.api.MotanBusinessService;
import com.wyt.motan.chatbusiness.util.ThreadPoolUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

/**
 * Created by wangyitao on 2018/11/28.
 */
@MotanService(basicService = "motanBusinessService")
public class MotanBusinessServiceImpl implements MotanBusinessService {

    @Value("${spring.application.name}")
    private String app;

    private static Logger logger = Logger.getLogger(ThreadPoolUtil.class);

    @Override
    public void processBusinessSync(long id, Map<String, Object> paramMap) {
        try {
            Thread.sleep(1000);
            logger.error("processBusiness ! id:" + id + ", paramMap:" + JSON.toJSONString(paramMap));
        } catch (Exception e) {
            logger.error("processBusiness error ! id:" + id + ", paramMap:" + JSON.toJSONString(paramMap), e);
        }
    }

    @Override
    public void processBusinessAsync(long id, Map<String, Object> paramMap) {
        ThreadPoolUtil.processBusiness(id, paramMap);
    }

}
