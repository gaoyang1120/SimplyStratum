package com.ileonidze.stratumCore;

import com.ileonidze.stratumIndicators.Indicator;
import com.ileonidze.stratumIndicators.IndicatorsCollection;
import org.apache.log4j.Logger;

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
                    Float indicatorValue = indicator.proceed(Integer.parseInt(command[1]),Integer.parseInt(command[2]),Integer.parseInt(command[3]),queryResult,null);
                    console.info(indicatorValue);
                }
                break;
            case "indicatorSimilarities":
                if(command.length<5){
                    console.error("Indicator's name, timeFrame, period, deviation or dataSet are not specified");
                }else{
                    String[] preDataSet = command[5].split(",");
                    Float[] dataSet = new Float[preDataSet.length];
                    for(int i=0;i<preDataSet.length;i++){
                        dataSet[i] = Float.parseFloat(preDataSet[i]);
                    }
                    String dataSetBuffer = "Dataset =";
                    for(int i = 0;i<dataSet.length;i++){
                        dataSetBuffer += " "+dataSet[i];
                    }
                    console.debug(dataSetBuffer);
                    List<Float[]> result = Database.findIndicatorSimilarities(new SearchConditions().setIndicator(command[1]).setTimeFrame(Integer.parseInt(command[2])).setPeriod(Integer.parseInt(command[3])).setDeviation(Float.parseFloat(command[4])).setInputData(dataSet));
                    if(result==null){
                        console.warn("Seems to be some problems occurred");
                    }else{
                        console.info("Found "+result.size()+" similarities");

                    }
                    console.info("Done");
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