package com.wyt.motan.chat.controller;

import com.alibaba.fastjson.JSON;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.wyt.motan.api.MotanBusinessService;
import com.wyt.motan.chat.util.ThreadPoolUtil;
import org.apache.log4j.Logger;
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

    private static Logger logger = Logger.getLogger(TestController.class);

    @MotanReferer(basicReferer = "motanBusinessClient")
    private MotanBusinessService motanBusinessService;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        Map<String, Object> map = new HashMap<>();
        map.put("vid", "vip1");
        motanBusinessService.processBusinessSync(1, map);
        return JSON.toJSONString(map);
    }

    @RequestMapping("count")
    public String count(int num) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            long id = i;
            motanBusinessService.processBusinessSync(id, new HashMap<>());
        }
        long end = System.currentTimeMillis();
        logger.error("count method cost:" + (end - start) + "ms");
        return null;
    }

    @RequestMapping("thread")
    public String thread(int num) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            long id = i;
            ThreadPoolUtil.processBusiness(id, new HashMap<>());
        }
        long end = System.currentTimeMillis();
        logger.error("thread method cost:" + (end - start) + "ms");
        return null;
    }
}
