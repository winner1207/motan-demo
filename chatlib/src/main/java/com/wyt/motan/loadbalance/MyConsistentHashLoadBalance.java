package com.wyt.motan.loadbalance;

import com.weibo.api.motan.cluster.loadbalance.AbstractLoadBalance;
import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.core.extension.SpiMeta;
import com.weibo.api.motan.rpc.Referer;
import com.weibo.api.motan.rpc.Request;
import com.weibo.api.motan.util.MathUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wangyitao on 2019/1/21.
 * 扩展负载均衡策略：一致性hash，基于默认 consistent【一致性hash】改造
 */
@SpiMeta(name = "myConsistent")
public class MyConsistentHashLoadBalance<T> extends AbstractLoadBalance<T> {

    private List<Referer<T>> consistentHashReferers;

    @Override
    public void onRefresh(List<Referer<T>> referers) {
        super.onRefresh(referers);

        List<Referer<T>> copyReferers = new ArrayList<Referer<T>>(referers);
        List<Referer<T>> tempRefers = new ArrayList<Referer<T>>();
        for (int i = 0; i < MotanConstants.DEFAULT_CONSISTENT_HASH_BASE_LOOP; i++) {
            Collections.shuffle(copyReferers);
            for (Referer<T> ref : copyReferers) {
                tempRefers.add(ref);
            }
        }

        consistentHashReferers = tempRefers;
    }

    @Override
    protected Referer<T> doSelect(Request request) {

        int hash = getHash(request);
        Referer<T> ref;
        for (int i = 0; i < getReferers().size(); i++) {
            ref = consistentHashReferers.get((hash + i) % consistentHashReferers.size());
            if (ref.isAvailable()) {
                return ref;
            }
        }
        return null;
    }

    @Override
    protected void doSelectToHolder(Request request, List<Referer<T>> refersHolder) {
        List<Referer<T>> referers = getReferers();

        int hash = getHash(request);
        for (int i = 0; i < referers.size(); i++) {
            Referer<T> ref = consistentHashReferers.get((hash + i) % consistentHashReferers.size());
            if (ref.isAvailable()) {
                refersHolder.add(ref);
            }
        }
    }

    private int getHash(Request request) {
        int hashcode;
        if (request.getArguments() == null || request.getArguments().length == 0) {
            hashcode = request.hashCode();
        } else {
            hashcode = Arrays.hashCode(request.getArguments());
        }
        return MathUtil.getPositive(hashcode);
    }


}
