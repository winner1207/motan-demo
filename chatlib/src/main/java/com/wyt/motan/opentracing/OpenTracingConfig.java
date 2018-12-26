package com.wyt.motan.opentracing;

import com.weibo.api.motan.filter.opentracing.OpenTracingContext;
import com.weibo.api.motan.filter.opentracing.TracerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangyitao on 2018/12/25.
 */
@Configuration
public class OpenTracingConfig {

    private static String serviceName;

    private static String address;

    public static String getServiceName() {
        return serviceName;
    }

    @Value("${rpc.opentracing.serviceName}")
    public void setServiceName(String serviceName) {
        OpenTracingConfig.serviceName = serviceName;
    }

    public static String getAddress() {
        return address;
    }

    @Value("${rpc.opentracing.address}")
    public void setAddress(String address) {
        OpenTracingConfig.address = address;
    }

    @Bean
    public BraveTarcerFactory tarcerFactory() {
        return new BraveTarcerFactory();
    }

    @Bean
    public OpenTracingContext openTracingContext(TracerFactory tracerFactory) {
        OpenTracingContext context = new OpenTracingContext();
        context.setTracerFactory(tracerFactory);
        return context;
    }
}
