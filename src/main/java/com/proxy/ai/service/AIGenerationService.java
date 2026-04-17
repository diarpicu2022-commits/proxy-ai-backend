package com.proxy.ai.service;

import com.proxy.ai.dto.GenerationRequest;
import com.proxy.ai.dto.GenerationResponse;

public interface AIGenerationService {
    GenerationResponse generate(GenerationRequest request);
}
