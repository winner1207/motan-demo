package com.wyt.motan.opentracing;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.core.extension.Activation;
import com.weibo.api.motan.core.extension.SpiMeta;
import com.weibo.api.motan.filter.Filter;
import com.weibo.api.motan.filter.opentracing.OpenTracingContext;
import com.weibo.api.motan.rpc.Caller;
import com.weibo.api.motan.rpc.Provider;
import com.weibo.api.motan.rpc.Request;
import com.weibo.api.motan.rpc.Response;
import com.weibo.api.motan.util.LoggerUtil;
import com.weibo.api.motan.util.MotanFrameworkUtil;
import io.opentracing.NoopTracer;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.Tracer.SpanBuilder;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMap;
import io.opentracing.propagation.TextMapExtractAdapter;

import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Created by wangyitao on 2018/12/25.
 */
@SpiMeta(name = "opentracing")
@Activation(sequence = 30, key = {MotanConstants.NODE_TYPE_SERVICE, MotanConstants.NODE_TYPE_REFERER})
public class OpenTracingFilter implements Filter {

    @Override
    public Response filter(Caller<?> caller, Request request) {
        Tracer tracer = getTracer();
        if (tracer == null || tracer instanceof NoopTracer) {
            return caller.call(request);
        }
        if (caller instanceof Provider) { // server end
            return processProviderTrace(tracer, caller, request);
        } else { // client end
            return processRefererTrace(tracer, caller, request);
        }
    }

    protected Tracer getTracer() {
        return OpenTracingContext.getTracer();
    }

    /**
     * process trace in client end
     *
     * @param caller
     * @param request
     * @return
     */
    protected Response processRefererTrace(Tracer tracer, Caller<?> caller, Request request) {
        String operationName = buildOperationName(request);
        SpanBuilder spanBuilder = tracer.buildSpan(operationName);
        Span activeSpan = OpenTracingContext.getActiveSpan();
        if (activeSpan != null) {
            spanBuilder.asChildOf(activeSpan);
        }
        Span span = spanBuilder.start();
        span.setTag("requestId", request.getRequestId());

        attachTraceInfo(tracer, span, request);
        return process(caller, request, span);

    }

    protected Response process(Caller<?> caller, Request request, Span span) {
        Exception ex = null;
        boolean exception = true;
        try {
            Response response = caller.call(request);
            if (response.getException() != null) {
                ex = response.getException();
            } else {
                exception = false;
            }
            return response;
        } catch (RuntimeException e) {
            ex = e;
            throw e;
        } finally {
            try {
                if (exception) {
                    span.log("request fail." + (ex == null ? "unknown exception" : ex.getMessage()));
                } else {
                    span.log("request success.");
                }
                span.finish();
            } catch (Exception e) {
                LoggerUtil.error("opentracing span finish error!", e);
            }
        }
    }

    protected String buildOperationName(Request request) {
        return "Motan_" + MotanFrameworkUtil.getGroupMethodString(request);
    }

    protected void attachTraceInfo(Tracer tracer, Span span, final Request request) {
        tracer.inject(span.context(), Format.Builtin.TEXT_MAP, new TextMap() {

            @Override
            public void put(String key, String value) {
                request.setAttachment(key, value);
            }

            @Override
            public Iterator<Entry<String, String>> iterator() {
                throw new UnsupportedOperationException("TextMapInjectAdapter should only be used with Tracer.inject()");
            }
        });
    }

    /**
     * process trace in server end
     *
     * @param caller
     * @param request
     * @return
     */
    protected Response processProviderTrace(Tracer tracer, Caller<?> caller, Request request) {
        Span span = extractTraceInfo(request, tracer);
        span.setTag("requestId", request.getRequestId());
        OpenTracingContext.setActiveSpan(span);
        return process(caller, request, span);
    }

    protected Span extractTraceInfo(Request request, Tracer tracer) {
        String operationName = buildOperationName(request);
        SpanBuilder span = tracer.buildSpan(operationName);
        try {
            SpanContext spanContext = tracer.extract(Format.Builtin.TEXT_MAP, new TextMapExtractAdapter(request.getAttachments()));
            if (spanContext != null) {
                span.asChildOf(spanContext);
            }
        } catch (Exception e) {
            span.withTag("Error", "extract from request fail, error msg:" + e.getMessage());
        }
        return span.start();
    }
}
