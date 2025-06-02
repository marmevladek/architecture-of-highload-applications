package ru.dating.app.profileservice.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<Boolean> isWrite = ThreadLocal.withInitial(() -> false);

    public static void setWrite(boolean write) {
        isWrite.set(write);
    }

    public static void clear() {
        isWrite.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return isWrite.get() ? "master" : "replica";
    }
}
