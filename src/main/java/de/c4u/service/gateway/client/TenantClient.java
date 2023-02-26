package de.c4u.service.gateway.client;

import de.c4u.service.gateway.dto.Tenant;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Thomas Uhlig
 */
public interface TenantClient {

    @GetExchange("query/tenant")
    Flux<Tenant> finalAll();

    @GetExchange("/query/tenant/{uuid}")
    Mono<Tenant> findOneByUuid(@PathVariable UUID uuid);
}
