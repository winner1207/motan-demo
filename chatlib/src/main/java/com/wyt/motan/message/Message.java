package com.wyt.motan.message;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by wangyitao on 2018/11/28.
 */
public abstract class Message implements Serializable {

    private MsgType mt;
    private long tm = System.currentTimeMillis();

    public abstract MsgType getMt();

    public long getTm() {
        return tm;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }
}
