package com.ocean.redis;

public abstract class BasePrefix implements KeyPrefix{

    private int expiredMills;
    private String prefix;

    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix(int expiredMills, String prefix) {
        this.expiredMills = expiredMills;
        this.prefix = prefix;
    }

    @Override
    public int expiredMills() {
        return expiredMills;
    }

    @Override 
    public String prefix() {
        String simpleName = getClass().getSimpleName();
        return simpleName + ":" + prefix;
    }
}
