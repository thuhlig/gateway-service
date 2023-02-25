package de.c4u.service.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    BusinessPartnerClient businessPartnerClient(WebClient.Builder builder) {
        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(builder.baseUrl("http://localhost:10011").build()))
                .build()
                .createClient(BusinessPartnerClient.class);
    }

    @Bean
    TenantClient tenantClient(WebClient.Builder builder) {
        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(builder.baseUrl("http://localhost:10021").build()))
                .build()
                .createClient(TenantClient.class);
    }
}

@Controller
class BusinessPartnerGraphQLController {

    private final BusinessPartnerClient businessPartnerClient;

    private final TenantClient tenantClient;

    BusinessPartnerGraphQLController(BusinessPartnerClient businessPartnerClient, TenantClient tenantClient) {
        this.businessPartnerClient = businessPartnerClient;
        this.tenantClient = tenantClient;
    }

    @QueryMapping
    Flux<BusinessPartner> findAllBusinessPartners() {
        return this.businessPartnerClient.findAll();
    }

    @QueryMapping
    Mono<BusinessPartner> findOneBusinessPartnerByUuid(@Argument UUID uuid) {
        return this.businessPartnerClient.findOneByUuid(uuid);
    }

    @SchemaMapping(typeName = "BusinessPartner")
    Mono<Tenant> tenant(BusinessPartner businessPartner) {
        return this.tenantClient.findOneByUuid(businessPartner.tenantUuid());
    }
}

record BusinessPartner(UUID uuid, String name, String name2, int groupCount, UUID tenantUuid, Tenant tenant) {
}

record Tenant(UUID uuid, String name, String code) {
}

interface BusinessPartnerClient {
    @GetExchange("/query/businessPartners")
    Flux<BusinessPartner> findAll();

    @GetExchange("/query/businessPartners/{uuid}")
    Mono<BusinessPartner> findOneByUuid(@PathVariable UUID uuid);
}

interface TenantClient {

    @GetExchange("query/tenant")
    Flux<Tenant> finalAll();

    @GetExchange("/query/tenant/{uuid}")
    Mono<Tenant> findOneByUuid(@PathVariable UUID uuid);
}
