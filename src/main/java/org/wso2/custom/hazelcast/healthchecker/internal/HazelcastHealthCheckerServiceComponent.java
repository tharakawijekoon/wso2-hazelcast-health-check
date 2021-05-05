package org.wso2.custom.hazelcast.healthchecker.internal;

import com.hazelcast.core.HazelcastInstance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.wso2.carbon.healthcheck.api.core.HealthChecker;
import org.wso2.custom.hazelcast.healthchecker.HazelcastHealthChecker;

@Component(
        name = "org.wso2.custom.hazelcast.healthchecker",
        immediate = true
)
public class HazelcastHealthCheckerServiceComponent {

    private static final Log log = LogFactory.getLog(HazelcastHealthCheckerServiceComponent.class);

    @Activate
    protected void activate(ComponentContext context) {

        try {
            BundleContext bundleContext = context.getBundleContext();
            bundleContext.registerService(HealthChecker.class.getName(), new HazelcastHealthChecker(), null);

            if (log.isDebugEnabled()) {
                log.debug("org.wso2.custom.hazelcast.healthchecker bundle is activated");
            }
        } catch (Throwable e) {
            log.error("Error while activating org.wso2.custom.hazelcast.healthchecker", e);
        }
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {

        if (log.isDebugEnabled()) {
            log.debug("org.wso2.custom.hazelcast.healthchecker bundle is deactivated");
        }
    }

    @Reference(
            name = "hazelcast.instance.service",
            service = com.hazelcast.core.HazelcastInstance.class,
            cardinality = ReferenceCardinality.OPTIONAL,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetHazelcastInstance")
    protected void setHazelcastInstance(HazelcastInstance hazelcastInstance) {

        HazelcastHealthCheckerServiceHolder.registerHazelcastInstance(hazelcastInstance);
    }

    protected void unsetHazelcastInstance(HazelcastInstance hazelcastInstance) {

        HazelcastHealthCheckerServiceHolder.registerHazelcastInstance(null);
    }
}
