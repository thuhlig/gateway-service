package de.c4u.service.gateway.controller;

import de.c4u.service.gateway.client.BusinessPartnerClient;
import de.c4u.service.gateway.client.TenantClient;
import de.c4u.service.gateway.dto.BusinessPartner;
import de.c4u.service.gateway.dto.Tenant;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Thomas Uhlig
 */
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
