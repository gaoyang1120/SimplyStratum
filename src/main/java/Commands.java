import org.apache.log4j.Logger;

public class Commands {
    private final static Logger console = Logger.getLogger(Main.class);
    public static void parse(String command){
        switch (command){
            case "whoami":
                console.debug("SimplyStratum");
                break;
            default:
                console.debug("Unknown command");
        }
    }
}
