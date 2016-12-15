package com.ileonidze.stratumIndicators;

import java.util.HashMap;

public class IndicatorsCollection {
    private static final HashMap<String,Indicator> collection = new HashMap<>();
    static {
        collection.put("RSI",new RSI());
        collection.put("RS",new RS());
    }
    public static Indicator getIndicator(String key) {
        return collection.get(key);
    }
}