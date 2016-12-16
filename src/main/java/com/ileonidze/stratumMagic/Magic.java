package com.ileonidze.stratumMagic;

import com.ileonidze.stratumCore.Database;
import com.ileonidze.stratumCore.DatabaseItem;
import com.ileonidze.stratumCore.SearchConditions;
import com.ileonidze.stratumIndicators.IndicatorsCollection;
import org.apache.log4j.Logger;

import java.util.List;

public class Magic {
    private final static Logger console = Logger.getLogger(Magic.class);
    private int period = 14;
    private float deviation = 6;
    private int futureDistance = 5;
    private int timeFrame = 1;
    private float actionThreshold = 76.5f;

    public int getPeriod() {
        return period;
    }
    public Magic setPeriod(int period) {
        this.period = period;
        return this;
    }
    public float getDeviation() {
        return deviation;
    }
    public Magic setDeviation(float deviation) {
        this.deviation = deviation;
        return this;
    }
    public int getFutureDistance() {
        return futureDistance;
    }
    public Magic setFutureDistance(int futureDistance) {
        this.futureDistance = futureDistance;
        return this;
    }
    public int getTimeFrame() {
        return timeFrame;
    }
    public Magic setTimeFrame(int timeFrame) {
        this.timeFrame = timeFrame;
        return this;
    }
    public float getActionThreshold() {
        return actionThreshold;
    }
    public Magic setActionThreshold(float actionThreshold) {
        this.actionThreshold = actionThreshold;
        return this;
    }

    public MagicResponse drop(MagicRequest request){
        String indicator = "RSI";

        DatabaseItem[] rsiDataset = new DatabaseItem[request.getDataSet().length-request.getIndicatorsPeriod()];
        for(int i=request.getIndicatorsPeriod();i<request.getDataSet().length;i++){
            console.info("Proceeding "+i);
            rsiDataset[i-request.getIndicatorsPeriod()] = IndicatorsCollection.getIndicator("RSI").proceed(i,1,request.getIndicatorsPeriod(),request.getDataSet()[i],request.getDataSet());
            console.info(request.getDataSet()[i].getValue()+" -> "+rsiDataset[i-request.getIndicatorsPeriod()]);
        }
        float[] simplifiedDRSIDataset = new float[rsiDataset.length];
        String rsiBuffer = "";
        for(int i=0;i<rsiDataset.length;i++){
            simplifiedDRSIDataset[i] = rsiDataset[i].getValue();
            rsiBuffer += ","+simplifiedDRSIDataset[i];
        }
        console.info("RSI: "+rsiBuffer);
        console.info("Indicator query length: "+simplifiedDRSIDataset.length);

        //// TODO: 16.12.2016 make async tests

        List<DatabaseItem[]> indicatorSimilarities = Database.findIndicatorSimilarities(new SearchConditions().setIndicator(indicator).setTimeFrame(timeFrame).setPeriod(request.getIndicatorsPeriod()).setDeviation(deviation).setFutureDistance(futureDistance).setInputData(simplifiedDRSIDataset).setStrictMovements(false));
        if(indicatorSimilarities==null) return null;
        int bulls = 0;
        int bears = 0;
        String totBuffer = "";
        if(indicatorSimilarities.size()==0){
            console.info("No similarities");
            return new MagicResponse();
        }
        for(int i=0;i<indicatorSimilarities.size();i++){
            String buffer = "\n["+i+" "+indicatorSimilarities.get(i)[0].getDate()+"]";
            //String buffer = "\n";
            for(int i2 = 0;i2<indicatorSimilarities.get(i).length;i2++){
                if(i2==rsiDataset.length) buffer += "  |  ";
                buffer += " "+indicatorSimilarities.get(i)[i2].getValue();
                if(i2==indicatorSimilarities.get(i).length-1){
                    if(indicatorSimilarities.get(i)[i2].getValue()>indicatorSimilarities.get(i)[rsiDataset.length-1].getValue()){
                        bulls++;
                    }else if(indicatorSimilarities.get(i)[i2].getValue()<indicatorSimilarities.get(i)[rsiDataset.length-1].getValue()){
                        bears++;
                    }
                    buffer += "        | "+(indicatorSimilarities.get(i)[i2].getValue()>indicatorSimilarities.get(i)[rsiDataset.length-1].getValue() ? "UP" : "DOWN");
                }
            }
            totBuffer += buffer;
        }
        console.info(totBuffer);
        console.info("Bulls "+bulls+"   vs   Bears "+bears);
        float bullsProbability = ((float) bulls/ (float) indicatorSimilarities.size()) * 100;
        float bearsProbability = ((float) bears/ (float) indicatorSimilarities.size()) * 100;
        int direction = 0;
        String directionString = "UNKNOWN";
        float probability = 50;
        if(bullsProbability>bearsProbability){
            direction = 1;
            probability = bullsProbability;
            directionString = "UP";
        }else if(bearsProbability>bullsProbability){
            direction = -1;
            probability = bearsProbability;
            directionString = "DOWN";
        }
        boolean act = probability > actionThreshold;
        return new MagicResponse().setDirectionNumber(direction).setDirectionNumber(direction).setDirectionString(directionString).setProbability(probability).setSimilarities(indicatorSimilarities).setAct(act);
    }
}
