package com.ileonidze.stratumIndicators;

import com.ileonidze.stratumCore.DatabaseItem;

public class RS extends Indicator {
    public final int size = 7;

    public final String name = this.getClass().getSimpleName();
    public final double proceed(int index, DatabaseItem item){

        return item.getPrice();
    }
}