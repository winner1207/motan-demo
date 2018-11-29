package com.wyt.motan.api;

import com.weibo.api.motan.transport.async.MotanAsync;

import java.util.Map;

/**
 * Created by wangyitao on 2018/11/28.
 */
@MotanAsync
public interface MotanBusinessService {

    public void processBusiness(long companyId, Map<String, Object> paramMap);
}
