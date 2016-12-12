package com.ileonidze.stratumIndicators;

import com.ileonidze.startumCore.DatabaseItem;

public class RSI extends Indicator {
    static double proceed(DatabaseItem item){
        return item.getValue();
    }
}