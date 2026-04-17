package com.proxy.ai.exception;

public class QuotaExceededException extends RuntimeException {
    public QuotaExceededException() {
        super("Monthly token quota exhausted. Please upgrade your plan.");
    }
}
