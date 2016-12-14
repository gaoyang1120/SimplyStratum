package com.ileonidze.stratumIndicators;

import java.util.HashMap;

public class IndicatorsCollection {
    private static final HashMap<String,Indicator> collection = new HashMap<>();
    static {
        collection.put("RSI",new RSI());
    }
    /*public static HashMap<String,Indicator> getCollection() {
        return collection;
    }*/
    public static Indicator getIndicator(String key) {
        return collection.get(key);
    }
}