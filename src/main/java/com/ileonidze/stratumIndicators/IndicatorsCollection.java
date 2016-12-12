package com.ileonidze.stratumIndicators;

import java.util.ArrayList;
import java.util.List;

public class IndicatorsCollection {
    private static final List<Indicator> collection = new ArrayList<>();
    static {
        collection.add(new RSI());
    }
    public static List<Indicator> getCollection() {
        return collection;
    }
}