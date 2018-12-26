package com.wyt.motan.opentracing;

import brave.Tracing;
import brave.opentracing.BraveTracer;
import com.weibo.api.motan.filter.opentracing.TracerFactory;
import io.opentracing.Tracer;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.okhttp3.OkHttpSender;

public class BraveTarcerFactory implements TracerFactory {

    private static Tracer braveTracer = BraveTracer.create(Tracing.newBuilder()
            // 设置当前服务的服务名
            .localServiceName(OpenTracingConfig.getServiceName())
            // 此处采用OkHttpSender通过HTTP请求的方式发送跟踪数据，Zipkin的日志接口地址需做成配置化的
            .spanReporter(AsyncReporter.create(OkHttpSender.create(OpenTracingConfig.getAddress())))
            .build());

    @SuppressWarnings("deprecation")
    @Override
    public Tracer getTracer() {
        return braveTracer;
    }

}
