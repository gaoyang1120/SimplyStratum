package com.ileonidze.stratumIndicators;

import com.ileonidze.stratumCore.Database;
import com.ileonidze.stratumCore.DatabaseItem;
import com.ileonidze.stratumCore.SearchConditions;

public abstract class Indicator {
    static double proceed(int index, DatabaseItem item){
        DatabaseItem foundCondition = Database.getItem(new SearchConditions().setIndex(index));
        return foundCondition == null ? item.getValue() : foundCondition.getValue();
    };
}