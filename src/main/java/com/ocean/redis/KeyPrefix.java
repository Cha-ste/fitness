package com.ocean.redis;

public interface KeyPrefix {
    int expiredMills();
    String prefix();
}
