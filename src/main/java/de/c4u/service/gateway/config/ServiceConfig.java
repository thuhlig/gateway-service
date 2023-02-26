package de.c4u.service.gateway.config;

import de.c4u.service.gateway.client.BusinessPartnerClient;
import de.c4u.service.gateway.client.TenantClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * @author Thomas Uhlig
 */
@Configuration
public class ServiceConfig {

    @Bean
    BusinessPartnerClient businessPartnerClient(WebClient.Builder builder, ServiceProperties serviceProperties) {
        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(builder.baseUrl(serviceProperties.getBusinessPartnerUrl()).build()))
                .build()
                .createClient(BusinessPartnerClient.class);
    }

    @Bean
    TenantClient tenantClient(WebClient.Builder builder, ServiceProperties serviceProperties) {
        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(builder.baseUrl(serviceProperties.getTenantUrl()).build()))
                .build()
                .createClient(TenantClient.class);
    }
}
