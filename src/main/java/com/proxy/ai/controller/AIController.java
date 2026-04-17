package com.proxy.ai.controller;

import com.proxy.ai.dto.GenerationRequest;
import com.proxy.ai.dto.GenerationResponse;
import com.proxy.ai.service.RateLimitProxyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final RateLimitProxyService rateLimitProxyService;

    public AIController(RateLimitProxyService rateLimitProxyService) {
        this.rateLimitProxyService = rateLimitProxyService;
    }

    @PostMapping("/generate")
    public ResponseEntity<GenerationResponse> generate(@RequestBody GenerationRequest request) {
        GenerationResponse response = rateLimitProxyService.generate(request);
        return ResponseEntity.ok(response);
    }
}
