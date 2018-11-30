package com.wyt.motan.chat.util;

import com.alibaba.fastjson.JSON;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.wyt.motan.api.MotanBusinessService;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangyitao on 2018/11/30.
 */
public class ThreadPoolUtil {

    private static Logger logger = Logger.getLogger(ThreadPoolUtil.class);

    private static ExecutorService executor = Executors.newFixedThreadPool(1000);

    @MotanReferer(basicReferer = "motanBusinessClient")
    private MotanBusinessService motanBusinessService;

    private static ThreadPoolUtil tpu = new ThreadPoolUtil();

    public static void processBusiness(final long id, final Map<String, Object> paramMap) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    tpu.motanBusinessService.processBusinessAsync(id, paramMap);
                } catch (Exception e) {
                    logger.error("processBusiness error ! id:" + id + ", paramMap:" + JSON.toJSONString(paramMap), e);
                }
            }
        });
    }

}
