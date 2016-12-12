package com.ileonidze.stratumCore;

import org.apache.log4j.Logger;

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
                if(command.length<3){
                    console.error("Index or indicator's name are not specified");
                }else{
                    DatabaseItem queryResult = Database.getItem(new SearchConditions().setIndex(Integer.parseInt(command[1])));
                    if(queryResult == null){
                        console.info("Record not found");
                        break;
                    }
                    Double indicatorValue = queryResult.getIndicators().get(command[2]);
                    console.info(indicatorValue == null ? "Indicator not found" : indicatorValue);
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