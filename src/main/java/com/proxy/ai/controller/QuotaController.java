package com.proxy.ai.controller;

import com.proxy.ai.dto.DailyUsage;
import com.proxy.ai.dto.QuotaStatusResponse;
import com.proxy.ai.model.UserPlan;
import com.proxy.ai.model.UserState;
import com.proxy.ai.service.UserStateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quota")
public class QuotaController {

    private final UserStateService userStateService;

    public QuotaController(UserStateService userStateService) {
        this.userStateService = userStateService;
    }

    @GetMapping("/status")
    public ResponseEntity<QuotaStatusResponse> getStatus(@RequestParam String userId) {
        UserState state = userStateService.getOrCreate(userId);
        UserPlan plan = state.getPlan();

        QuotaStatusResponse resp = new QuotaStatusResponse();
        resp.setPlan(plan.name());
        resp.setTokensUsed(state.getTokensUsedThisMonth());
        resp.setTotalTokens(plan == UserPlan.ENTERPRISE ? Long.MAX_VALUE : plan.getTokensPerMonth());
        resp.setTokensRemaining(
                plan == UserPlan.ENTERPRISE
                        ? Long.MAX_VALUE
                        : Math.max(0, plan.getTokensPerMonth() - state.getTokensUsedThisMonth())
        );
        resp.setResetDate(LocalDate.now().withDayOfMonth(1).plusMonths(1).toString());
        resp.setRequestsThisMinute(state.getRequestsThisMinute());
        resp.setRequestsPerMinute(plan.getRequestsPerMinute());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/history")
    public ResponseEntity<List<DailyUsage>> getHistory(@RequestParam String userId) {
        UserState state = userStateService.getOrCreate(userId);
        List<DailyUsage> history = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd");

        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            var atomicVal = state.getDailyTokenUsage().get(date);
            long tokens = atomicVal != null ? atomicVal.get() : 0L;
            history.add(new DailyUsage(date.format(fmt), tokens));
        }
        return ResponseEntity.ok(history);
    }

    @PostMapping("/upgrade")
    public ResponseEntity<Map<String, String>> upgrade(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        String targetPlan = body.getOrDefault("plan", "PRO");
        UserState state = userStateService.getOrCreate(userId);

        try {
            state.setPlan(UserPlan.valueOf(targetPlan.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid plan: " + targetPlan));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Plan upgraded to " + state.getPlan().name(),
                "plan", state.getPlan().name()
        ));
    }
}
