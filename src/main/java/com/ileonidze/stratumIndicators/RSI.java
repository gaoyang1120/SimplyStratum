package com.ileonidze.stratumIndicators;

import com.ileonidze.stratumCore.DatabaseItem;

import java.util.HashMap;

class RSI extends Indicator {
    public final String name = "RSI";
    private HashMap<String,Float> cache = new HashMap<>();
    public final Float proceed(int index, int timeFrame, int period, DatabaseItem item){
        String queryKey = item.getTimestamp()+"_"+timeFrame+"_"+period;
        Float cachedValue = cache.get(queryKey);
        if(cachedValue!=null){
            return cachedValue;
        }
        // TODO: проверки на нормальность
        Float rsi = 100-(100/(1+(new RS().proceed(index, timeFrame, period, item))));
        cache.put(queryKey,rsi);
        return rsi;
    }
}