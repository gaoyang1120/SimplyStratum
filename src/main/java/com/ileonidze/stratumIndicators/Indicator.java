package com.ileonidze.stratumIndicators;

import com.ileonidze.startumCore.DatabaseItem;

public abstract class Indicator {
    static double proceed(DatabaseItem item){
        return item.getValue();
    };
}
