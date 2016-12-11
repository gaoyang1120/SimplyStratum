import org.apache.log4j.Logger;

public class Commands {
    private final static Logger console = Logger.getLogger(Main.class);
    public static void parse(String[] command){
        switch (command[0]){
            case "whoami":
                console.debug("SimplyStratum");
                break;
            case "load":
                console.debug("Loading data");
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

            default:
                console.debug("Unknown command");
        }
    }
}
