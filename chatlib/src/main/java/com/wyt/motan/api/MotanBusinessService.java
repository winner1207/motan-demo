package com.wyt.motan.api;

import java.util.Map;

/**
 * Created by wangyitao on 2018/11/28.
 */
public interface MotanBusinessService {

    /**
     * 同步
     *
     * @param companyId
     * @param paramMap
     */
    public void processBusinessSync(long id, Map<String, Object> paramMap);

    /**
     * 异步
     *
     * @param id
     * @param paramMap
     */
    public void processBusinessAsync(long id, Map<String, Object> paramMap);
}
