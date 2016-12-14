package com.ileonidze.stratumIndicators;

import com.ileonidze.stratumCore.DatabaseItem;

class RS extends Indicator {
    public final String name = this.getClass().getSimpleName();
    public final Float proceed(int index, int timeFrame, int period, DatabaseItem item){

        return item.getPrice();
    }
}