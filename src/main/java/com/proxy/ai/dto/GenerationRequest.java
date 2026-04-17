package com.proxy.ai.dto;

public class GenerationRequest {
    private String userId;
    private String prompt;

    public GenerationRequest() {}

    public GenerationRequest(String userId, String prompt) {
        this.userId = userId;
        this.prompt = prompt;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }
}
