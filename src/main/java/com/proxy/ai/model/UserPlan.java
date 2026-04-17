package com.proxy.ai.model;

public enum UserPlan {
    FREE(10, 50_000L),
    PRO(60, 500_000L),
    ENTERPRISE(Integer.MAX_VALUE, Long.MAX_VALUE);

    private final int requestsPerMinute;
    private final long tokensPerMonth;

    UserPlan(int requestsPerMinute, long tokensPerMonth) {
        this.requestsPerMinute = requestsPerMinute;
        this.tokensPerMonth = tokensPerMonth;
    }

    public int getRequestsPerMinute() { return requestsPerMinute; }
    public long getTokensPerMonth() { return tokensPerMonth; }
}
