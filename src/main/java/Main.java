import org.apache.log4j.Logger;

import java.util.Scanner;

public class Main {
    private final static Logger console = Logger.getLogger(Main.class);
    private final static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        console.debug("");
        console.debug("SimplyStratum is running as new instance");
        while(sc.hasNext()) {
            String s1 = sc.next();
            console.trace(">>>: "+s1);
            if(s1.equals("exit")||s1.equals("shutdown")) {
                shutdown();
                break;
            }else{
                String[] s2 = s1.split(" ");
                for(int i=0;i<s2.length;i++){
                    s2[i] = s2[i].replace("\\t"," ");
                }
                execute(s2);
            }
        }
    }
    public static void execute(String[] command){
        Commands.parse(command);
    }
    public static void shutdown(){
        console.debug("Shutting down");
    }
}