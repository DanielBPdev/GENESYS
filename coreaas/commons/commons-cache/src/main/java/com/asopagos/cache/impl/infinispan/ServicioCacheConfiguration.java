package com.asopagos.cache.impl.infinispan;

import java.util.concurrent.TimeUnit;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;

public class ServicioCacheConfiguration extends MutableConfiguration<String, String> {

    private static final long serialVersionUID = -3958094708190969130L;

    public ServicioCacheConfiguration() {

        Integer segundos = 20;

        this.setTypes(String.class, String.class)
            .setCacheLoaderFactory(new ServicioCacheLoaderFactory())
            .setReadThrough(true)
            .setExpiryPolicyFactory(
                    TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, segundos))
                    );
    }

}
