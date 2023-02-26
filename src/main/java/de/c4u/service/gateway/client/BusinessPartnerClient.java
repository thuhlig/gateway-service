package de.c4u.service.gateway.client;

import de.c4u.service.gateway.dto.BusinessPartner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Thomas Uhlig
 */
public interface BusinessPartnerClient {
    @GetExchange("/query/businessPartners")
    Flux<BusinessPartner> findAll();

    @GetExchange("/query/businessPartners/{uuid}")
    Mono<BusinessPartner> findOneByUuid(@PathVariable UUID uuid);
}
