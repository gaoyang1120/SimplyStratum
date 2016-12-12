package com.ileonidze.stratumIndicators;

import com.ileonidze.stratumCore.DatabaseItem;

public class RSI extends Indicator {
    public final String name = this.getClass().getSimpleName();
    public final double proceed(int index, DatabaseItem item){
        return item.getValue();
    }
}