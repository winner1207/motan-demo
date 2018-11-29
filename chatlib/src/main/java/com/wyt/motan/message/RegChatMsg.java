package com.wyt.motan.message;

/**
 * Created by wangyitao on 2018/11/28.
 */
public class RegChatMsg extends Message {

    @Override
    public MsgType getMt() {
        return MsgType.regChat;
    }
}
