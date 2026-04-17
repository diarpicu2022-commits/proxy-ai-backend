package com.proxy.ai.scheduler;

import com.proxy.ai.service.UserStateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ResetScheduler {

    private final UserStateService userStateService;

    public ResetScheduler(UserStateService userStateService) {
        this.userStateService = userStateService;
    }

    @Scheduled(fixedRate = 60_000)
    public void resetRateLimits() {
        userStateService.resetAllRateLimits();
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void resetMonthlyQuotas() {
        userStateService.resetAllMonthlyQuotas();
    }
}
