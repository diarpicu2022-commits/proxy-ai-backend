package com.proxy.ai.model;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class UserState {

    private volatile UserPlan plan;
    private final AtomicLong tokensUsedThisMonth = new AtomicLong(0);
    private final AtomicInteger requestsThisMinute = new AtomicInteger(0);
    private final ConcurrentHashMap<LocalDate, AtomicLong> dailyTokenUsage = new ConcurrentHashMap<>();

    public UserState(UserPlan plan) {
        this.plan = plan;
    }

    public UserPlan getPlan() { return plan; }
    public void setPlan(UserPlan plan) { this.plan = plan; }

    public long getTokensUsedThisMonth() { return tokensUsedThisMonth.get(); }

    public void addTokens(long tokens) {
        tokensUsedThisMonth.addAndGet(tokens);
        dailyTokenUsage
                .computeIfAbsent(LocalDate.now(), k -> new AtomicLong(0))
                .addAndGet(tokens);
    }

    public int getRequestsThisMinute() { return requestsThisMinute.get(); }

    public int incrementAndGetRequests() { return requestsThisMinute.incrementAndGet(); }

    public void resetRateLimit() { requestsThisMinute.set(0); }

    public void resetMonthlyQuota() { tokensUsedThisMonth.set(0); }

    public ConcurrentHashMap<LocalDate, AtomicLong> getDailyTokenUsage() { return dailyTokenUsage; }
}
