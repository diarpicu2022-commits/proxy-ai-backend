package com.proxy.ai.service;

import com.proxy.ai.model.UserPlan;
import com.proxy.ai.model.UserState;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserStateService {

    private final ConcurrentHashMap<String, UserState> users = new ConcurrentHashMap<>();

    public UserState getOrCreate(String userId) {
        return users.computeIfAbsent(userId, k -> new UserState(UserPlan.FREE));
    }

    public void resetAllRateLimits() {
        users.values().forEach(UserState::resetRateLimit);
    }

    public void resetAllMonthlyQuotas() {
        users.values().forEach(UserState::resetMonthlyQuota);
    }
}
