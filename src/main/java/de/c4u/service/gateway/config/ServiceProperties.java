package de.c4u.service.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Thomas Uhlig
 */
@Configuration
@ConfigurationProperties(prefix = "de.c4u.gateway")
public class ServiceProperties {

    private String businessPartnerUrl;

    private String tenantUrl;

    public String getBusinessPartnerUrl() {
        return businessPartnerUrl;
    }

    public void setBusinessPartnerUrl(String businessPartnerUrl) {
        this.businessPartnerUrl = businessPartnerUrl;
    }

    public String getTenantUrl() {
        return tenantUrl;
    }

    public void setTenantUrl(String tenantUrl) {
        this.tenantUrl = tenantUrl;
    }
}
