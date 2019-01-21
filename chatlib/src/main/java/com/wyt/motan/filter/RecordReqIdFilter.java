package com.wyt.motan.filter;

import com.weibo.api.motan.core.extension.Activation;
import com.weibo.api.motan.core.extension.Scope;
import com.weibo.api.motan.core.extension.Spi;
import com.weibo.api.motan.core.extension.SpiMeta;
import com.weibo.api.motan.filter.Filter;
import com.weibo.api.motan.rpc.Caller;
import com.weibo.api.motan.rpc.Request;
import com.weibo.api.motan.rpc.Response;
import org.apache.log4j.Logger;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by wangyitao on 2019/1/4.
 * 记录请求ID
 */
@Spi(scope = Scope.SINGLETON)
@SpiMeta(name = "recordReqId")
@Activation(sequence = 30)
public class RecordReqIdFilter implements Filter {

    private static Logger logger = Logger.getLogger(RecordReqIdFilter.class);

    private static Set<Long> reqS = new ConcurrentSkipListSet<>();

    @Override
    public Response filter(Caller<?> caller, Request request) {
        long reqId = request.getRequestId();
        reqS.add(reqId);
        logger.error("motan recordReqIdFilter add reqId:" + reqId + ", reqS size:" + reqS.size());
        Response response = caller.call(request);
        reqS.remove(reqId);
        logger.error("motan recordReqIdFilter remove reqId:" + reqId + ", reqS size:" + reqS.size());
        return response;
    }
}
