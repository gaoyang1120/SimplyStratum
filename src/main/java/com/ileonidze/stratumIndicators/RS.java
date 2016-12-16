package com.ileonidze.stratumIndicators;

import com.ileonidze.stratumCore.Database;
import com.ileonidze.stratumCore.DatabaseItem;
import com.ileonidze.stratumCore.SearchConditions;

import java.util.Date;

class RS extends Indicator {
    public final DatabaseItem proceed(int index, int timeFrame, int period, DatabaseItem item, DatabaseItem[] additionalSource){
        float up = 0;
        float down = 0;
        if(additionalSource!=null&&additionalSource.length<period+1) return new DatabaseItem(0,item.getDate());
        for(int i=0;i<period;i++){
            DatabaseItem thisObject;
            DatabaseItem previousObject;
            if(additionalSource==null) {
                thisObject = Database.getItem(new SearchConditions().setIndex(index - i).setTimeFrame(timeFrame));
                previousObject = Database.getItem(new SearchConditions().setIndex(index - i - 1).setTimeFrame(timeFrame));
            }else{
                thisObject = additionalSource[index-i];
                previousObject = index-i-1<0 ? null : additionalSource[index-i-1];
            }
            if(thisObject!=null&&previousObject!=null){
                float difference = thisObject.getValue()-previousObject.getValue();
                if(difference>0){
                    up += difference;
                }else if(difference<0){
                    down += difference;
                }
            }
        }
        return new DatabaseItem((up)/(down*-1),item.getDate());
    }
}