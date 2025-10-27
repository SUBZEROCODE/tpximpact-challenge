
package com.subzero.tpximpact_challenge.util;

import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.health.*;

public class AlwaysUpHealthEndpointGroup implements HealthEndpointGroup {
    @Override
    public boolean isMember(String name) {
        return true; // include all indicators
    }

    @Override
    public boolean showComponents(SecurityContext securityContext) {
        return true; // always show component-level health
    }

    @Override
    public boolean showDetails(SecurityContext securityContext) {
        return true; // always show full health details
    }

    @Override
    public StatusAggregator getStatusAggregator() {
        return StatusAggregator.getDefault(); // use default status aggregation
    }

    @Override
    public HttpCodeStatusMapper getHttpCodeStatusMapper() {
        return HttpCodeStatusMapper.DEFAULT; // use default HTTP status mapping
    }

    @Override
    public AdditionalHealthEndpointPath getAdditionalPath() {
        return null; // no additional path used
    }
    
}
