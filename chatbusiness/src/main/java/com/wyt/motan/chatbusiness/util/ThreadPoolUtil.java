package com.wyt.motan.chatbusiness.util;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangyitao on 2018/11/29.
 */
public class ThreadPoolUtil {

    private static Logger logger = Logger.getLogger(ThreadPoolUtil.class);

    private static ExecutorService executor = Executors.newFixedThreadPool(1000);

    public static void processBusiness(final long id, final Map<String, Object> paramMap) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1 * 1000);
                    logger.error("processBusiness ! id:" + id + ", paramMap:" + JSON.toJSONString(paramMap));
                } catch (Exception e) {
                    logger.error("processBusiness error ! id:" + id + ", paramMap:" + JSON.toJSONString(paramMap), e);
                }
            }
        });
    }

}
