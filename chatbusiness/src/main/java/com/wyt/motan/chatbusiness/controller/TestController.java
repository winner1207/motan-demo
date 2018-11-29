package com.wyt.motan.chatbusiness.controller;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.wyt.motan.api.MotanChatService;
import com.wyt.motan.message.RegChatMsg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangyitao on 2018/11/29.
 */
@RestController
public class TestController {

    @MotanReferer(basicReferer = "motanChatClient")
    MotanChatService motanChatService;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        motanChatService.broadMsg("18", new RegChatMsg());
        return null;
    }
}
