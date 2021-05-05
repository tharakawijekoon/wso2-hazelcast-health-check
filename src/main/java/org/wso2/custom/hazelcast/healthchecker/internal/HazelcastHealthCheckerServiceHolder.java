package org.wso2.custom.hazelcast.healthchecker.internal;

import com.hazelcast.core.HazelcastInstance;

public class HazelcastHealthCheckerServiceHolder {
    private static HazelcastInstance hazelcastInstance;

    public static void registerHazelcastInstance(HazelcastInstance hazelcastInstance) {
        HazelcastHealthCheckerServiceHolder.hazelcastInstance = hazelcastInstance;
    }

    public static HazelcastInstance getHazelcastInstance() {
        return hazelcastInstance;
    }
}
