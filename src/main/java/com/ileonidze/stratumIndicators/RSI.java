package com.ileonidze.stratumIndicators;

import com.ileonidze.stratumCore.DatabaseItem;

public class RSI extends Indicator {
    static double proceed(int index, DatabaseItem item){
        return item.getValue();
    }
}