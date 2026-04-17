package com.proxy.ai.controller;

import com.proxy.ai.dto.GenerationRequest;
import com.proxy.ai.dto.GenerationResponse;
import com.proxy.ai.service.CacheProxyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final CacheProxyService cacheProxyService;

    public AIController(CacheProxyService cacheProxyService) {
        this.cacheProxyService = cacheProxyService;
    }

    @PostMapping("/generate")
    public ResponseEntity<GenerationResponse> generate(@RequestBody GenerationRequest request) {
        GenerationResponse response = cacheProxyService.generate(request);
        return ResponseEntity.ok(response);
    }
}
