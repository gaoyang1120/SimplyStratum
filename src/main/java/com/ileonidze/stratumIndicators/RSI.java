package com.ileonidze.stratumIndicators;

import com.ileonidze.stratumCore.DatabaseItem;

import java.util.HashMap;

class RSI extends Indicator {
    private HashMap<String,Float> cache = new HashMap<>();
    public final Float proceed(int index, int timeFrame, int period, DatabaseItem item){
        String queryKey = item.getTimestamp()+"_"+timeFrame+"_"+period;
        Float cachedValue = cache.get(queryKey);
        if(cachedValue!=null){
            return cachedValue;
        }
        Float rs = IndicatorsCollection.getIndicator("RS").proceed(index, timeFrame, period, item);
        Float rsi = 100-(100/(1+(rs)));
        cache.put(queryKey,rsi);
        return rsi;
    }
}