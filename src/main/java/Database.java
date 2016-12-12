import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

public class Database {
    private final static Logger console = Logger.getLogger(Main.class);
    private static DatabaseItem[] data = null;
    public static boolean load(String dirPath){
        if(data != null) return false;
        long startTime = new Date().getTime();
        int succeededFiles = 0;
        int pushedItems = 0;
        int totalItems = 0;
        long totalTime = 0;
        File databaseDirectoryObject = new File(dirPath);
        File[] directoryList = databaseDirectoryObject.listFiles();
        if(directoryList==null || directoryList.length == 0){
            return false;
        }
        console.trace("Constructing database from "+directoryList.length+" files");
        for (final File fileEntry : directoryList) {
            if (!fileEntry.isDirectory()) {
                console.debug("Loading database file "+fileEntry.getPath());
                try{
                    BufferedReader br = new BufferedReader(new FileReader(fileEntry));
                    for(String lineString; (lineString = br.readLine()) != null; ) {
                        totalItems++;
                        DatabaseItem item = convertCSVStringToDatabaseItem(lineString);
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
                console.trace("Pushed "+pushedItems+" items from "+totalItems+" (over "+Math.floor(pushedItems/totalItems*1000)/10+"%) for "+proceedingTimeDifference+"ms");
                succeededFiles++;
            }
        }
        long proceedingTimeDifference = (new Date().getTime()-startTime);
        console.trace("Totally pushed "+pushedItems+" items from "+totalItems+" (over "+Math.floor(pushedItems/totalItems*1000)/10+"%) for "+proceedingTimeDifference+"ms");
        return true;
    }
    static private DatabaseItem convertCSVStringToDatabaseItem(String string){
        CSVItem convertedItem = new CSVItem(string);
        if(convertedItem.getDate()==null) return null;
        return new DatabaseItem(convertedItem);
    }
    public static boolean unload(){
        data = null;
        return true;
    }
    public static boolean reload(String dirPath){
        return unload() && load(dirPath);
    }
}