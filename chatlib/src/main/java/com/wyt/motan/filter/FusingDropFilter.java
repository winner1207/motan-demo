package com.wyt.motan.filter;

import com.alibaba.fastjson.JSON;
import com.weibo.api.motan.core.extension.Activation;
import com.weibo.api.motan.core.extension.Scope;
import com.weibo.api.motan.core.extension.Spi;
import com.weibo.api.motan.core.extension.SpiMeta;
import com.weibo.api.motan.exception.MotanServiceException;
import com.weibo.api.motan.filter.Filter;
import com.weibo.api.motan.rpc.Caller;
import com.weibo.api.motan.rpc.Request;
import com.weibo.api.motan.rpc.Response;
import org.apache.log4j.Logger;

/**
 * Created by wangyitao on 2019/1/4.
 * 熔断降级测试
 */
@Spi(scope = Scope.SINGLETON)
@SpiMeta(name = "fusingDrop")
@Activation(sequence = 30)
public class FusingDropFilter implements Filter {

    private static Logger logger = Logger.getLogger(FusingDropFilter.class);

    @Override
    public Response filter(Caller<?> caller, Request request) {
        Response response = null;
        try {
            response = caller.call(request);
            return response;
        } catch (MotanServiceException ex) {
            logger.error("motan fusingDropFilter error! caller:" + JSON.toJSONString(caller) + ", request:" + JSON.toJSONString(request), ex);
            //捕捉到异常后，判断是否触发熔断机制，做降级处理：此处的降级策略为由替补组执行
            return response;
        } catch (Exception e) {
            throw e;
        }
    }
}
