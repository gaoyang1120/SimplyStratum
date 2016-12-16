package com.ileonidze.stratumCore;

import com.ileonidze.stratumIndicators.Indicator;
import com.ileonidze.stratumIndicators.IndicatorsCollection;
import com.ileonidze.stratumMagic.Magic;
import com.ileonidze.stratumMagic.MagicRequest;
import com.ileonidze.stratumMagic.MagicResponse;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

public class Commands {
    private final static Logger console = Logger.getLogger(Commands.class);
    public static void parse(String[] command){
        switch (command[0]){
            case "whoami":
                console.debug("SimplyStratum");
                break;
            case "help":
                console.debug("Commands supported:"
                +"\nwhoami"
                +"\nhelp"
                +"\nload"
                +"\nreload"
                +"\nunload"
                +"\nsetSortable"
                +"\ngetSize"
                +"\ngetItem"
                +"\nsearch"
                +"\ntest"
                +"");
                break;
            case "reload":
                console.debug("Reloading...");
                if(command.length<2){
                    console.error("Incorrect data path specified");
                }else{
                    printConsoleResult(Database.reload(command[1]));
                }
                break;
            case "load":
                console.debug("Loading...");
                if(command.length<2){
                    console.error("Incorrect data path specified");
                }else{
                    printConsoleResult(Database.load(command[1]));
                }
                break;
            case "unload":
                console.debug("Unloading...");
                printConsoleResult(Database.unload());
                break;
            case "setSortable":
                if(command.length<2){
                    console.error("Incorrect set value specified");
                }else{
                    printConsoleResult(Database.setSortable(command[1]));
                }
                break;
            case "getItem":
                if(command.length<3){
                    console.error("Index or timeFrame are not specified");
                }else{
                    DatabaseItem queryResult = Database.getItem(new SearchConditions().setIndex(Integer.parseInt(command[1])).setTimeFrame(Integer.parseInt(command[2])));
                    console.info(queryResult == null ? "Not found" : queryResult);
                }
                break;
            case "getIndicator":
                if(command.length<5){
                    console.error("Index, timeFrame, period or indicator's name are not specified");
                }else{
                    DatabaseItem queryResult = Database.getItem(new SearchConditions().setIndex(Integer.parseInt(command[1])).setTimeFrame(Integer.parseInt(command[2])));
                    if(queryResult == null){
                        console.info("Record not found");
                        break;
                    }
                    Indicator indicator = IndicatorsCollection.getIndicator(command[4]);
                    if(indicator == null){
                        console.info("Indicator not found");
                        break;
                    }
                    float indicatorValue = indicator.proceed(Integer.parseInt(command[1]),Integer.parseInt(command[2]),Integer.parseInt(command[3]),queryResult,null).getValue();
                    console.info(indicatorValue);
                }
                break;
            case "magic":
                if(command.length<3){
                    console.error("Period or dataSet are not specified");
                }else{
                    String[] requestDataStrings = command[2].split(",");
                    DatabaseItem[] requestDataSet = new DatabaseItem[requestDataStrings.length];
                    for(int i=0;i<requestDataStrings.length;i++){
                        requestDataSet[i] = new DatabaseItem(Float.parseFloat(requestDataStrings[i]),new Date());
                    }
                    MagicResponse magic = new Magic().drop(new MagicRequest().setIndicatorsPeriod(Integer.parseInt(command[1])).setDataSet(requestDataSet));
                    if(magic == null){
                        console.warn("Unknown problem occurred");
                        break;
                    }
                    console.info((magic.isAct() ? "ACT" : "WAIT")+", direction "+magic.getDirectionString()+", confidence "+Math.floor(magic.getProbability()*100)/100+"%");
                }
                break;
            case "indicatorSimilarities":
                if(command.length<5){
                    console.error("Indicator's name, timeFrame, period, deviation, futureDistance or dataSet are not specified");
                }else{
                    Date startDate = new Date();
                    String[] preDataSet = command[6].split(",");
                    float[] dataSet = new float[preDataSet.length];
                    for(int i=0;i<preDataSet.length;i++){
                        dataSet[i] = Float.parseFloat(preDataSet[i]);
                    }
                    String dataSetBuffer = "Dataset =";
                    for (float aDataSet : dataSet) {
                        dataSetBuffer += " " + aDataSet;
                    }
                    console.debug(dataSetBuffer);
                    List<DatabaseItem[]> result = Database.findIndicatorSimilarities(new SearchConditions().setIndicator(command[1]).setTimeFrame(Integer.parseInt(command[2])).setPeriod(Integer.parseInt(command[3])).setDeviation(Float.parseFloat(command[4])).setFutureDistance(Integer.parseInt(command[5])).setInputData(dataSet).setStrictMovements(false));
                    if(result==null){
                        console.warn("Seems to be some problems occurred");
                    }else{
                        String totBuffer = "Found "+result.size()+" similarities";
                        float bulls = 0;
                        float bears = 0;
                        float tots = 0;
                        for(int i=0;i<result.size();i++){
                            tots++;
                            String buffer = "\n["+i+" "+result.get(i)[0].getDate()+"]";
                            //String buffer = "\n";
                            for(int i2 = 0;i2<result.get(i).length;i2++){
                                //if(i2==dataSet.length) buffer += "  |  ";
                                //buffer += " "+result.get(i)[i2];
                                if(i2==result.get(i).length-1){
                                    if(result.get(i)[i2].getValue()>result.get(i)[dataSet.length-1].getValue()){
                                        bulls++;
                                    }else if(result.get(i)[i2].getValue()<result.get(i)[dataSet.length-1].getValue()){
                                        bears++;
                                    }
                                    buffer += "        | "+(result.get(i)[i2].getValue()>result.get(i)[dataSet.length-1].getValue() ? "UP" : "DOWN");
                                }
                            }
                            totBuffer += buffer;
                        }
                        console.info(totBuffer);
                        float bullsProbability = bulls/tots*100;
                        float bearsProbability = bears/tots*100;
                        //console.info("Bulls "+bulls+" ("+bullsProbability+"%), Bears "+bears+" ("+bearsProbability+"%) from "+tots);
                        int direction = 0;
                        float probability = 50;
                        if(bullsProbability>bearsProbability){
                            direction = 1;
                            probability = bullsProbability;
                        }else if(bearsProbability>bullsProbability){
                            direction = -1;
                            probability = bearsProbability;
                        }
                        console.info("Result: "+(direction > 0 ? "UP" : direction < 0 ? "DOWN" : "UNKNOWN")+" in "+Math.floor(probability)+"%, "+(probability > 76 ? "ACT" : "SKIP"));
                    }
                    console.info("Done for "+(new Date().getTime()-startDate.getTime())+"ms");
                }
                break;
            case "getSize":
                console.info(Database.getSize());
                break;
            case "search":
                console.warn("Not implemented");
                break;
            case "test":
                console.warn("Not implemented");
                break;
            default:
                console.debug("Unknown command");
        }
    }
    private static void printConsoleResult(boolean state){
        if(state){
            console.info("Success");
        }else{
            console.error("Failure");
        }
    }
}