package com.ileonidze.startumCore;

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