package com.subzero.tpximpact_challenge.util;

import java.time.Duration;
import java.util.Collections;

import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.HealthEndpointGroups;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.boot.health.registry.DefaultHealthContributorRegistry;
import org.springframework.boot.health.registry.DefaultReactiveHealthContributorRegistry;
import org.springframework.boot.health.registry.HealthContributorRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpHealthConfig {

    @Bean
    public HealthContributorRegistry healthContributorRegistry() {
        DefaultHealthContributorRegistry registry = new DefaultHealthContributorRegistry();
        registry.registerContributor("custom", (HealthIndicator) () -> Health.up().build());
        return registry;
    }

    @Bean
    public HealthEndpointGroups healthEndpointGroups() {
        return HealthEndpointGroups.of( new AlwaysUpHealthEndpointGroup(), Collections.emptyMap());
    }

    @Bean
    public HealthEndpoint healthEndpoint(HealthContributorRegistry registry, HealthEndpointGroups groups) {
        return new HealthEndpoint(registry, new DefaultReactiveHealthContributorRegistry(), groups, Duration.ZERO);
    }
}

