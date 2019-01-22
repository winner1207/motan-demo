package com.wyt.motan.config;

import com.weibo.api.motan.config.springsupport.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangyitao on 2018/11/28.
 */
@Configuration
public class MotanConfig {

    @Bean
    @ConfigurationProperties(prefix = "rpc.common.annotation")
    public AnnotationBean motanAnnotationBean() {
        AnnotationBean motanAnnotationBean = new AnnotationBean();
        return motanAnnotationBean;
    }

    @Bean(name = "motanProtocol")
    @ConfigurationProperties(prefix = "rpc.common.protocol")
    public ProtocolConfigBean protocolConfig() {
        ProtocolConfigBean config = new ProtocolConfigBean();
        return config;
    }

    @Bean(name = "motanRegistry")
    @ConfigurationProperties(prefix = "rpc.common.registry")
    public RegistryConfigBean registryConfig() {
        RegistryConfigBean config = new RegistryConfigBean();
        return config;
    }

    @Bean(name = "motanBusinessClient")
    @ConfigurationProperties(prefix = "rpc.business.client")
    public BasicRefererConfigBean baseRefererConfig() {
        BasicRefererConfigBean config = new BasicRefererConfigBean();
        return config;
    }

    @Bean(name = "motanBusinessService")
    @ConfigurationProperties(prefix = "rpc.business.server")
    public BasicServiceConfigBean baseServiceConfig() {
        BasicServiceConfigBean config = new BasicServiceConfigBean();
        return config;
    }
}
