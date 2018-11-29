package com.wyt.motan.api;

import com.weibo.api.motan.transport.async.MotanAsync;
import com.wyt.motan.message.Message;

/**
 * Created by wangyitao on 2018/11/28.
 */
@MotanAsync
public interface MotanChatService {

    public void broadMsg(String companyId, Message message);
}
