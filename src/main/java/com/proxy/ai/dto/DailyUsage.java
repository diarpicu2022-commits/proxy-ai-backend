package com.proxy.ai.dto;

public class DailyUsage {
    private String date;
    private long tokensUsed;

    public DailyUsage(String date, long tokensUsed) {
        this.date = date;
        this.tokensUsed = tokensUsed;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public long getTokensUsed() { return tokensUsed; }
    public void setTokensUsed(long tokensUsed) { this.tokensUsed = tokensUsed; }
}
