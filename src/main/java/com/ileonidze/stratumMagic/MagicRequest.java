package com.ileonidze.stratumMagic;

import com.ileonidze.stratumCore.DatabaseItem;

public class MagicRequest {
    private DatabaseItem[] dataSet;
    private int indicatorsPeriod = 0;

    public DatabaseItem[] getDataSet() {
        return dataSet;
    }

    public MagicRequest setDataSet(DatabaseItem[] dataSet) {
        this.dataSet = dataSet;
        return this;
    }

    public int getIndicatorsPeriod() {
        return indicatorsPeriod;
    }

    public MagicRequest setIndicatorsPeriod(int indicatorsPeriod) {
        this.indicatorsPeriod = indicatorsPeriod;
        return this;
    }
}