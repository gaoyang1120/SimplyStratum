package com.ileonidze.stratumIndicators;

import com.ileonidze.stratumCore.DatabaseItem;

public class RS extends Indicator {
    public final String name = this.getClass().getSimpleName();
    public final double proceed(int index, int timeFrame, int period, DatabaseItem item){

        return item.getPrice();
    }
}