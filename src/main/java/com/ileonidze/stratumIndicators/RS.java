package com.ileonidze.stratumIndicators;

import com.ileonidze.stratumCore.Commands;
import com.ileonidze.stratumCore.Database;
import com.ileonidze.stratumCore.DatabaseItem;
import com.ileonidze.stratumCore.SearchConditions;
import org.apache.log4j.Logger;

class RS extends Indicator {
    private final static Logger console = Logger.getLogger(Commands.class);
    public final String name = this.getClass().getSimpleName();
    public final Float proceed(int index, int timeFrame, int period, DatabaseItem item){
        Float up = new Float(0);
        Float down = new Float(0);
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
                //console.debug(thisObject.getPrice()+" / "+previousObject.getPrice()+" / "+difference);
            }
        }
        /*console.debug("UP="+up/period);
        console.debug("DOWN="+down*-1/period);*/
        return (up/period)/(down*-1/period);
    }
}