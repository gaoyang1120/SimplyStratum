import org.apache.log4j.Logger;

public class Commands {
    private final static Logger console = Logger.getLogger(Main.class);
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
                printConsoleResult(Database.reload());
                break;
            case "load":
                console.debug("Loading...");
                printConsoleResult(Database.load());
                break;
            case "unload":
                console.debug("Unloading...");
                printConsoleResult(Database.unload());
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
