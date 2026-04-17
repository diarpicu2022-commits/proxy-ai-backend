package com.proxy.ai.service;

import com.proxy.ai.dto.GenerationRequest;
import com.proxy.ai.dto.GenerationResponse;
import com.proxy.ai.exception.QuotaExceededException;
import com.proxy.ai.model.UserPlan;
import com.proxy.ai.model.UserState;
import org.springframework.stereotype.Service;

@Service
public class QuotaProxyService implements AIGenerationService {

    private final AIGenerationService next;
    private final UserStateService userStateService;

    public QuotaProxyService(MockAIGenerationService mockAIGenerationService,
                              UserStateService userStateService) {
        this.next = mockAIGenerationService;
        this.userStateService = userStateService;
    }

    @Override
    public GenerationResponse generate(GenerationRequest request) {
        UserState state = userStateService.getOrCreate(request.getUserId());
        UserPlan plan = state.getPlan();

        if (plan != UserPlan.ENTERPRISE
                && state.getTokensUsedThisMonth() >= plan.getTokensPerMonth()) {
            throw new QuotaExceededException();
        }

        GenerationResponse response = next.generate(request);
        state.addTokens(response.getTokensUsed());
        return response;
    }
}
