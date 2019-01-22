package com.wyt.motan.api;

import com.alibaba.fastjson.JSON;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import com.wyt.motan.util.ThreadPoolUtil;
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

    private static Logger logger = Logger.getLogger(MotanBusinessServiceImpl.class);

    @Override
    public void processBusinessSync(long id, Map<String, Object> paramMap) {
        logger.error("processBusinessSync ! app:" + app + ", id:" + id + ", paramMap:" + JSON.toJSONString(paramMap));
    }

    @Override
    public void processBusinessAsync(long id, Map<String, Object> paramMap) {
        logger.error("processBusinessAsync ! app:" + app + ", id:" + id + ", paramMap:" + JSON.toJSONString(paramMap));
        ThreadPoolUtil.processBusiness(id, paramMap);
    }

}
