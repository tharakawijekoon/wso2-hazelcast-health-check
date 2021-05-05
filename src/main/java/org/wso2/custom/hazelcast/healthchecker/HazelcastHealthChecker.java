package org.wso2.custom.hazelcast.healthchecker;

import com.hazelcast.cluster.ClusterState;
import com.hazelcast.core.HazelcastInstance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.healthcheck.api.core.HealthChecker;
import org.wso2.carbon.healthcheck.api.core.exception.BadHealthException;
import org.wso2.carbon.healthcheck.api.core.model.HealthCheckerConfig;
import org.wso2.custom.hazelcast.healthchecker.internal.HazelcastHealthCheckerServiceHolder;

import java.util.Properties;

public class HazelcastHealthChecker implements HealthChecker {

    private static final Log log = LogFactory.getLog(HazelcastHealthChecker.class);
    protected HealthCheckerConfig healthCheckerConfig = null;
    private static final String HAZELCAST_HEALTH_CHECKER = "HazelcastHealthChecker";

    @Override
    public String getName() {

        return HAZELCAST_HEALTH_CHECKER;
    }

    @Override
    public void init(HealthCheckerConfig healthCheckerConfig) {

        this.healthCheckerConfig = healthCheckerConfig;
    }

    @Override
    public Properties checkHealth() throws BadHealthException {
        Properties properties = new Properties();
        HazelcastInstance hazelcastInstance = HazelcastHealthCheckerServiceHolder.getHazelcastInstance();
        ClusterState state = hazelcastInstance.getCluster().getClusterState();
        int size = hazelcastInstance.getCluster().getMembers().size();
        log.info("Cluster status : " + state);
        log.info("Number of members : " + size);
        properties.put("cluster.status", state);
        properties.put("cluster.members", size);
        if (size == 0) {
            throw new BadHealthException("500", "Cluster not running, zero members");
        }
        return properties;
    }

    @Override
    public boolean isEnabled() {

        return this.healthCheckerConfig == null || healthCheckerConfig.isEnable();
    }

    @Override
    public int getOrder() {

        if (this.healthCheckerConfig == null) {
            return 0;
        } else {
            return healthCheckerConfig.getOrder();
        }
    }
}
