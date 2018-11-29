package com.wyt.motan.chatbusiness.api;

import com.alibaba.fastjson.JSON;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import com.wyt.motan.api.MotanBusinessService;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

/**
 * Created by wangyitao on 2018/11/28.
 */
@MotanService(basicService = "motanBusinessService")
public class MotanBusinessServiceImpl implements MotanBusinessService {

    @Value("${spring.application.name}")
    private String app;


    @Override
    public void processBusiness(long companyId, Map<String, Object> paramMap) {
        System.out.println(app + " broadMsg, companyId:" + companyId + ", paramMap:" + mapToJson(paramMap));
    }

    public String mapToJson(Map<String, Object> map) {
        return JSON.toJSONString(map);
    }
}
