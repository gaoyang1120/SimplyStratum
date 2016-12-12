package com.ileonidze.stratumIndicators;

import com.ileonidze.stratumCore.DatabaseItem;

public class RSI extends Indicator {
    public final String name = this.getClass().getSimpleName();
    public final double proceed(int index, int timeFrame, int period, DatabaseItem item){
        double rs = new RS().proceed(index, timeFrame, period, item);
        return item.getPrice();
    }
}