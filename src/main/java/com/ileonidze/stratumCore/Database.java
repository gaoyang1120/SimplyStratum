package com.ileonidze.stratumCore;

import com.ileonidze.stratumIndicators.Indicator;
import com.ileonidze.stratumIndicators.IndicatorsCollection;
import org.apache.log4j.Logger;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Database {
    private final static Logger console = Logger.getLogger(Database.class);
    private static DatabaseItem[] data = null;
    private static boolean sortable = false;

    /* LOADING MANIPULATIONS */
    static boolean load(String dirPath){
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
    static boolean unload(){
        data = null;
        return true;
    }
    static boolean reload(String dirPath){
        return unload() && load(dirPath);
    }
    static boolean setSortable(String setString){
        if(!setString.equals("1")&&!setString.equals("0")&&!setString.equals("true")&&!setString.equals("false")&&!setString.equals("+")&&!setString.equals("-")&&!setString.equals("yes")&&!setString.equals("no")) return false;
        sortable = (setString.equals("1")||setString.equals("+")||setString.equals("true")||setString.equals("yes"));
        return true;
    }

    /* DATA PROCEEDING MANIPULATIONS */
    static int getSize(){
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
    static List<Float[]> findIndicatorSimilarities(SearchConditions conditions){
        if(conditions.getIndicator()==null){
            console.error("No indicator specified");
            return null;
        }
        if(conditions.getInputData()==null||conditions.getInputData().length<1){
            console.error("No input data specified");
            return null;
        }
        Indicator indicator = IndicatorsCollection.getIndicator(conditions.getIndicator());
        if(indicator==null){
            console.error("Indicator is not found");
            return null;
        }
        // // FIXME: 15.12.2016 deathPointer can produce nullpointerexception
        List<Float[]> results = new ArrayList<>();
        for(int i=0;i<(conditions.getDeathPointer()==null ? (data.length-conditions.getInputData().length) : (conditions.getDeathPointer()>-1 ? conditions.getDeathPointer() : data.length-conditions.getDeathPointer()));i++){
            Float[] successCase = new Float[conditions.getInputData().length+conditions.getFutureDistance()];
            for(int i2=0;i2<conditions.getInputData().length;i2++){
                Float databaseItem = indicator.proceed(i+i2,conditions.getTimeFrame(),conditions.getPeriod(),data[i+i2],null);
                boolean similar = databaseItem<conditions.getInputData()[i2]+conditions.getDeviation()&&databaseItem>conditions.getInputData()[i2]-conditions.getDeviation();
                //console.debug(databaseItem);
                if(!similar){
                    i2 = conditions.getInputData().length+1;
                }else{
                    successCase[i2] = databaseItem;
                    if(i2==conditions.getInputData().length-1){
                        for(int i3=1;i3<conditions.getFutureDistance();i3++){
                            successCase[i2+i3] = indicator.proceed(i+i2+i3,conditions.getTimeFrame(),conditions.getPeriod(),data[i+i2+i3],null);
                        }
                        results.add(successCase);
                    }
                }
            }
        }
        return results;
    }
    public static DatabaseItem[][] findSimilarities(SearchConditions conditions){
        console.debug("findSimilarities is here");
        if(conditions.getIndicators()==null||conditions.getIndicators().length<1){
            console.error("No indicators specified");
            return null;
        }
        if(conditions.getInputData()==null||conditions.getInputData().length<1){
            console.error("No input data specified");
            return null;
        }

        // // FIXME: 15.12.2016 deathPointer can produce nullpointerexception
        // for test purposes make demo-search on first indicator
        DatabaseItem[][] results = null;
        for(int i=conditions.getPeriod();i<(conditions.getDeathPointer()==null ? (data.length-conditions.getInputData().length) : (conditions.getDeathPointer()>-1 ? conditions.getDeathPointer() : data.length-conditions.getDeathPointer()));i++){
            for(int i2=conditions.getPeriod();i2<conditions.getInputData().length;i2++){

            }
        }


        /*
        if(results==null){
            // if results null, then make first raw prediction
        }else{
            // if results is not null, then make predictions, based on

        }
        */

        return null;
    }
}