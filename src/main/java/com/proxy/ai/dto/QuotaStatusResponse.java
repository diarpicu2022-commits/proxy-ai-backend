package com.proxy.ai.dto;

public class QuotaStatusResponse {
    private String plan;
    private long tokensUsed;
    private long tokensRemaining;
    private long totalTokens;
    private String resetDate;
    private int requestsThisMinute;
    private int requestsPerMinute;

    public QuotaStatusResponse() {}

    public String getPlan() { return plan; }
    public void setPlan(String plan) { this.plan = plan; }
    public long getTokensUsed() { return tokensUsed; }
    public void setTokensUsed(long tokensUsed) { this.tokensUsed = tokensUsed; }
    public long getTokensRemaining() { return tokensRemaining; }
    public void setTokensRemaining(long tokensRemaining) { this.tokensRemaining = tokensRemaining; }
    public long getTotalTokens() { return totalTokens; }
    public void setTotalTokens(long totalTokens) { this.totalTokens = totalTokens; }
    public String getResetDate() { return resetDate; }
    public void setResetDate(String resetDate) { this.resetDate = resetDate; }
    public int getRequestsThisMinute() { return requestsThisMinute; }
    public void setRequestsThisMinute(int requestsThisMinute) { this.requestsThisMinute = requestsThisMinute; }
    public int getRequestsPerMinute() { return requestsPerMinute; }
    public void setRequestsPerMinute(int requestsPerMinute) { this.requestsPerMinute = requestsPerMinute; }
}
