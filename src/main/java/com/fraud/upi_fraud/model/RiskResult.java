package com.fraud.upi_fraud.model;

import java.util.List;

public class RiskResult {
    private int score;
    private String level;
    private List<String> reasons;

    public RiskResult(int score, String level, List<String> reasons) {
        this.score = score;
        this.level = level;
        this.reasons = reasons;
    }

    public int getScore() { return score; }
    public String getLevel() { return level; }
    public List<String> getReasons() { return reasons; }
}