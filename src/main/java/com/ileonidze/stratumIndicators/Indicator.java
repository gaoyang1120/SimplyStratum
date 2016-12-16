package com.ileonidze.stratumIndicators;

import com.ileonidze.stratumCore.Database;
import com.ileonidze.stratumCore.DatabaseItem;
import com.ileonidze.stratumCore.SearchConditions;

public abstract class Indicator {
    public DatabaseItem proceed(int index, int timeFrame, int period, DatabaseItem item, DatabaseItem[] additionalSource){
        DatabaseItem foundCondition = Database.getItem(new SearchConditions().setIndex(index).setTimeFrame(timeFrame));
        return item;
    }
}