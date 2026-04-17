package com.proxy.ai.service;

import com.proxy.ai.dto.GenerationRequest;
import com.proxy.ai.dto.GenerationResponse;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheProxyService implements AIGenerationService {

    private final AIGenerationService next;

    public CacheProxyService(RateLimitProxyService rateLimitProxyService) {
        this.next = rateLimitProxyService;
    }

    private final Map<String, CachedResponse> globalCache = new ConcurrentHashMap<>();

    @Override
    public GenerationResponse generate(GenerationRequest request) {
        String cacheKey = generateCacheKey(request.getUserId(), request.getPrompt());
        
        CachedResponse cached = globalCache.get(cacheKey);
        if (cached != null) {
            return new GenerationResponse(
                cached.responseText,
                cached.tokensUsed,
                0L
            );
        }

        GenerationResponse response = next.generate(request);

        globalCache.put(cacheKey, new CachedResponse(
            response.getGeneratedText(),
            response.getTokensUsed()
        ));

        return response;
    }

    private String generateCacheKey(String userId, String prompt) {
        return userId + "::" + prompt.toLowerCase().trim();
    }

    private static class CachedResponse {
        final String responseText;
        final int tokensUsed;

        CachedResponse(String responseText, int tokensUsed) {
            this.responseText = responseText;
            this.tokensUsed = tokensUsed;
        }
    }
}