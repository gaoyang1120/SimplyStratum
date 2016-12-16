package com.ileonidze.stratumIndicators;

import com.ileonidze.stratumCore.DatabaseItem;

import java.util.HashMap;

class RSI extends Indicator {
    private HashMap<String,DatabaseItem> cache = new HashMap<>();
    public final DatabaseItem proceed(int index, int timeFrame, int period, DatabaseItem item, DatabaseItem[] additionalSource){
        String queryKey = item.getTimestamp()+"_"+timeFrame+"_"+period;
        if(additionalSource==null) {
            DatabaseItem cachedValue = cache.get(queryKey);
            if (cachedValue != null) {
                return cachedValue;
            }
        }
        DatabaseItem rsi = new DatabaseItem(100-(100/(1+(IndicatorsCollection.getIndicator("RS").proceed(index, timeFrame, period, item, additionalSource).getValue()))),item.getDate());
        if(additionalSource==null) cache.put(queryKey,rsi);
        return rsi;
    }
}