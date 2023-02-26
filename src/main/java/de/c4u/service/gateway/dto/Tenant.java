package de.c4u.service.gateway.dto;

import java.util.UUID;

/**
 * @author Thomas Uhlig
 */
public record Tenant(UUID uuid, String name, String code) {
}
