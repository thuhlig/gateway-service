package de.c4u.service.gateway.dto;

import java.util.UUID;

/**
 * @author Thomas Uhlig
 */
public record BusinessPartner(UUID uuid, String name, String name2, int groupCount, UUID tenantUuid, Tenant tenant) {
}
