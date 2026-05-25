package com.fraud.upi_fraud.engine;

import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VelocityTracker {

    private final Map<String, List<Long>> txnLog = new ConcurrentHashMap<>();

    public int getRecentCount(String payerId, long windowMillis) {
        long now = System.currentTimeMillis();
        txnLog.putIfAbsent(payerId, new ArrayList<>());
        List<Long> timestamps = txnLog.get(payerId);
        timestamps.removeIf(t -> (now - t) > windowMillis);
        timestamps.add(now);
        return timestamps.size();
    }
}