package com.ileonidze.stratumCore;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.Date;

public class Database {
    private final static Logger console = Logger.getLogger(Database.class);
    private static DatabaseItem[] data = null;
    private static boolean sortable = false;

    /* LOADING MANIPULATIONS */
    public static boolean load(String dirPath){
        if(data != null) return false;
        long startTime = new Date().getTime();
        int pushedItems = 0;
        int totalItems = 0;
        File databaseDirectoryObject = new File(dirPath);
        File[] directoryList = databaseDirectoryObject.listFiles();
        if(directoryList==null || directoryList.length == 0){
            return false;
        }
        int totalLines = 0;
        console.debug("Constructing database from "+directoryList.length+" files");
        for (final File fileEntry : directoryList) {
            if (!fileEntry.isDirectory()) {
                console.debug("Checking database file "+fileEntry.getPath());
                try{
                    LineNumberReader lnr = new LineNumberReader(new FileReader(new File(fileEntry.getPath())));
                    lnr.skip(Long.MAX_VALUE);
                    totalLines += lnr.getLineNumber() + 1;
                    lnr.close();
                }catch(Exception e){
                    console.error("Something went wrong for file "+fileEntry.getPath());
                }
            }
        }
        data = new DatabaseItem[totalLines];
        for (final File fileEntry : directoryList) {
            if (!fileEntry.isDirectory()) {
                console.debug("Loading database file "+fileEntry.getPath());
                try{
                    BufferedReader br = new BufferedReader(new FileReader(fileEntry));
                    for(String lineString; (lineString = br.readLine()) != null; ) {
                        totalItems++;
                        DatabaseItem item = convertCSVStringToDatabaseItem(lineString,pushedItems+1);
                        if(item != null){
                            pushedItems++;
                            data[pushedItems-1] = item;
                        }
                    }
                }catch(IOException e){
                    console.error("File "+fileEntry.getPath()+" reading problem");
                    e.printStackTrace();
                }
                long proceedingTimeDifference = (new Date().getTime()-startTime);
                console.debug("Pushed "+pushedItems+" items from "+totalItems+" (over "+Math.floor(pushedItems/totalItems*1000)/10+"%) for "+proceedingTimeDifference+"ms");
            }
        }
        if(sortable) Arrays.sort(data);
        long proceedingTimeDifference = (new Date().getTime()-startTime);
        console.debug("Totally pushed "+pushedItems+" items from "+totalItems+" (over "+Math.floor(pushedItems/totalItems*1000)/10+"%) for "+proceedingTimeDifference+"ms");
        return true;
    }
    static private DatabaseItem convertCSVStringToDatabaseItem(String string, int index){
        CSVItem convertedItem = new CSVItem(string);
        if(convertedItem.getDate()==null) return null;
        return new DatabaseItem(convertedItem, index);
    }
    public static boolean unload(){
        data = null;
        return true;
    }
    public static boolean reload(String dirPath){
        return unload() && load(dirPath);
    }
    public static boolean setSortable(String setString){
        if(!setString.equals("1")&&!setString.equals("0")&&!setString.equals("true")&&!setString.equals("false")&&!setString.equals("+")&&!setString.equals("-")&&!setString.equals("yes")&&!setString.equals("no")) return false;
        sortable = (setString.equals("1")||setString.equals("+")||setString.equals("true")||setString.equals("yes"));
        return true;
    }

    /* DATA PROCEEDING MANIPULATIONS */
    public static int getSize(){
        if(data==null){
            console.warn("Database is empty");
            return 0;
        }
        return data.length;
    }
    public static DatabaseItem getItem(SearchConditions conditions){
        if(conditions.getIndex()!=null){
            int realIndex = conditions.getIndex()*conditions.getTimeFrame();
            if(data==null) {
                console.warn("Database is empty!");
                return null;
            }else if(Math.abs(realIndex)>data.length-1){
                console.warn("Out of collection");
                return null;
            }else{
                return data[realIndex<0 ? (data.length+realIndex) : realIndex];
            }
        }
        return null;
    }
}