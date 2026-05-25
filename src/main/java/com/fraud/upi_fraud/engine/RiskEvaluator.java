package com.fraud.upi_fraud.engine;

import com.fraud.upi_fraud.model.RiskResult;
import com.fraud.upi_fraud.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RiskEvaluator {

    @Autowired
    private VelocityTracker velocityTracker;

    private final Map<String, String> deviceHistory = new ConcurrentHashMap<>();
    private final Map<String, List<String>> merchantHistory = new ConcurrentHashMap<>();
    private static final long FIVE_MINUTES_MS = 5 * 60 * 1000;

    public RiskResult evaluate(Transaction tx) {
        int score = 0;
        List<String> reasons = new ArrayList<>();

        // Rule 1 — high amount
        if (tx.getAmount() >= 10000) {
            score += 30;
            reasons.add("Amount >= 10000");
        } else if (tx.getAmount() >= 5000) {
            score += 10;
            reasons.add("Amount >= 5000");
        }

        // Rule 2 — new merchant
        merchantHistory.putIfAbsent(tx.getPayerId(), new ArrayList<>());
        List<String> knownMerchants = merchantHistory.get(tx.getPayerId());
        boolean isNewMerchant = !knownMerchants.contains(tx.getPayeeId());
        if (isNewMerchant && tx.getAmount() >= 5000) {
            score += 30;
            reasons.add("New merchant with high amount");
        } else if (isNewMerchant) {
            score += 10;
            reasons.add("First-time merchant");
        }
        knownMerchants.add(tx.getPayeeId());

        // Rule 3 — velocity
        int recentCount = velocityTracker.getRecentCount(tx.getPayerId(), FIVE_MINUTES_MS);
        if (recentCount > 10) {
            score += 30;
            reasons.add("High velocity: " + recentCount + " tx in 5 min");
        } else if (recentCount > 5) {
            score += 15;
            reasons.add("Moderate velocity: " + recentCount + " tx in 5 min");
        }

        // Rule 4 — unusual hour
        try {
            LocalDateTime dt = LocalDateTime.parse(
                tx.getTimestamp(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            );
            int hour = dt.getHour();
            if (hour >= 0 && hour <= 4) {
                score += 20;
                reasons.add("Unusual hour: " + hour + ":00");
            }
        } catch (Exception ignored) {}

        // Rule 5 — device change
        String lastDevice = deviceHistory.get(tx.getPayerId());
        if (lastDevice != null && !lastDevice.equals(tx.getDeviceId())) {
            score += 20;
            reasons.add("Device changed: " + lastDevice + " to " + tx.getDeviceId());
            if (isNewMerchant) {
                score += 10;
                reasons.add("Device change + new merchant combo");
            }
        }
        deviceHistory.put(tx.getPayerId(), tx.getDeviceId());

        score = Math.min(score, 100);
        String level = score >= 70 ? "HIGH" : score >= 40 ? "MEDIUM" : "LOW";
        return new RiskResult(score, level, reasons);
    }
}