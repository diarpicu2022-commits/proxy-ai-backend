package com.proxy.ai.dto;

public class GenerationResponse {
    private String generatedText;
    private int tokensUsed;
    private long processingTimeMs;

    public GenerationResponse() {}

    public GenerationResponse(String generatedText, int tokensUsed, long processingTimeMs) {
        this.generatedText = generatedText;
        this.tokensUsed = tokensUsed;
        this.processingTimeMs = processingTimeMs;
    }

    public String getGeneratedText() { return generatedText; }
    public void setGeneratedText(String generatedText) { this.generatedText = generatedText; }
    public int getTokensUsed() { return tokensUsed; }
    public void setTokensUsed(int tokensUsed) { this.tokensUsed = tokensUsed; }
    public long getProcessingTimeMs() { return processingTimeMs; }
    public void setProcessingTimeMs(long processingTimeMs) { this.processingTimeMs = processingTimeMs; }
}
