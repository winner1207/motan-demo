package com.wyt.motan.chat.api;

import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import com.wyt.motan.api.MotanChatService;
import com.wyt.motan.message.Message;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by wangyitao on 2018/11/28.
 */
@MotanService(basicService = "motanChatService")
public class MotanChatServiceImpl implements MotanChatService {

    @Value("${spring.application.name}")
    private String app;

    @Override
    public void broadMsg(String companyId, Message message) {
        System.out.println(app + " broadMsg, companyId:" + companyId + ", message" + message.toJson());
    }
}
