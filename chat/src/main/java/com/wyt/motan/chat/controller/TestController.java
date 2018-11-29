package com.wyt.motan.chat.controller;

import com.alibaba.fastjson.JSON;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.wyt.motan.api.MotanBusinessService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangyitao on 2018/11/29.
 */
@RestController
public class TestController {

    @MotanReferer(basicReferer = "motanBusinessClient")
    MotanBusinessService motanBusinessService;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        Map<String, Object> map = new HashMap<>();
        map.put("vid", "vip1");
        motanBusinessService.processBusiness(18L, map);
        return JSON.toJSONString(map);
    }
}
