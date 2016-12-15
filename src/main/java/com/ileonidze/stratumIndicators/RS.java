package com.ileonidze.stratumIndicators;

import com.ileonidze.stratumCore.Database;
import com.ileonidze.stratumCore.DatabaseItem;
import com.ileonidze.stratumCore.SearchConditions;

class RS extends Indicator {
    public final String name = this.getClass().getSimpleName();
    public final Float proceed(int index, int timeFrame, int period, DatabaseItem item){
        Float up = 0f;
        Float down = 0f;
        for(int i=0;i<period;i++){
            DatabaseItem thisObject = Database.getItem(new SearchConditions().setIndex(index-i).setTimeFrame(timeFrame));
            DatabaseItem previousObject = Database.getItem(new SearchConditions().setIndex(index-i-1).setTimeFrame(timeFrame));
            if(thisObject!=null&&previousObject!=null){
                Float difference = thisObject.getPrice()-previousObject.getPrice();
                if(difference>0){
                    up += difference;
                }else if(difference<0){
                    down += difference;
                }
            }
        }
        return (up/period)/(down*-1/period);
    }
}