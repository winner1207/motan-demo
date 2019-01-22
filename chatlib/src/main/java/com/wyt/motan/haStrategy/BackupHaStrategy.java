package com.wyt.motan.haStrategy;

import com.alibaba.fastjson.JSON;
import com.weibo.api.motan.cluster.LoadBalance;
import com.weibo.api.motan.cluster.ha.FailoverHaStrategy;
import com.weibo.api.motan.core.extension.SpiMeta;
import com.weibo.api.motan.exception.MotanServiceException;
import com.weibo.api.motan.rpc.Referer;
import com.weibo.api.motan.rpc.Request;
import com.weibo.api.motan.rpc.Response;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by wangyitao on 2019/1/21.
 * 扩展可用性策略：备用组，基于默认 failover【失效切换】改造
 */
@SpiMeta(name = "backup")
public class BackupHaStrategy<T> extends FailoverHaStrategy<T> {

    private static Logger logger = Logger.getLogger(BackupHaStrategy.class);

    private List<Referer<T>> refererList = null;
    private List<Referer<T>> backupRefererList = null;

    @Override
    public Response call(Request request, LoadBalance<T> loadBalance) {
        Response response = null;
        try {
            refererList = super.selectReferers(request, loadBalance);
            response = super.call(request, loadBalance);
            return response;
        } catch (MotanServiceException ex1) {
            //捕捉到异常后，判断是否触发熔断机制，做降级处理：此处的降级策略为由替补组执行
            logger.error("backupHaStrategy ready to switch backup group ! request:" + JSON.toJSONString(request));
            return response;
        } catch (Exception e) {
            throw e;
        }
    }
}
