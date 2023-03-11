package de.c4u.service.gateway.config;

import de.c4u.service.gateway.client.BusinessPartnerClient;
import de.c4u.service.gateway.client.TenantClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

/**
 * @author Thomas Uhlig
 */
@Configuration
public class ServiceConfig {

    public static Logger log = LogManager.getLogger();

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.debug("Request: Method: [{}]  URL: {}", clientRequest.method(), clientRequest.url());
            log.debug("Headers: {}", clientRequest.headers());
            log.debug("Payload: {}", clientRequest.body());

            return Mono.just(clientRequest);
        });
    }

    @Bean
    BusinessPartnerClient businessPartnerClient(WebClient.Builder builder, ServiceProperties serviceProperties,
                                                ReactiveClientRegistrationRepository clientRegistrations,
                                                ServerOAuth2AuthorizedClientRepository authorizedClients) {

        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                clientRegistrations, authorizedClients);
        oauth.setDefaultOAuth2AuthorizedClient(true);

        var client = builder.filter(oauth).filter(this.logRequest()).baseUrl(serviceProperties.getBusinessPartnerUrl()).build();

        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
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
