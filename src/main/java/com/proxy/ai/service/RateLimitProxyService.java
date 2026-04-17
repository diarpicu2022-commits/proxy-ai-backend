package com.proxy.ai.service;

import com.proxy.ai.dto.GenerationRequest;
import com.proxy.ai.dto.GenerationResponse;
import com.proxy.ai.exception.RateLimitExceededException;
import com.proxy.ai.model.UserState;
import org.springframework.stereotype.Service;

@Service
public class RateLimitProxyService implements AIGenerationService {

    private final AIGenerationService next;
    private final UserStateService userStateService;

    public RateLimitProxyService(QuotaProxyService quotaProxyService,
                                  UserStateService userStateService) {
        this.next = quotaProxyService;
        this.userStateService = userStateService;
    }

    @Override
    public GenerationResponse generate(GenerationRequest request) {
        UserState state = userStateService.getOrCreate(request.getUserId());
        int currentRequests = state.incrementAndGetRequests();

        if (currentRequests > state.getPlan().getRequestsPerMinute()) {
            state.resetRateLimit();
            throw new RateLimitExceededException(60);
        }

        return next.generate(request);
    }
}
