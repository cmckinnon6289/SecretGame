package zork;


public class CommandWords {

    private static final String[] VALID_COMMANDS = {
            "quit", "help", "eat", "get", "run", "grab", "up", "down", "drink", "open", "close",
            "look", "search", "strike", "rest", "north", "east",
            "west", "south", "exit", "attack", "pick up", "unlock", "go"};

   
    public boolean isCommand(String aString) { 
        for (String c : VALID_COMMANDS) { 
            if (c.equalsIgnoreCase(aString))
                return true;
        }
        return false; 
    }

    public String getLowerCaseForm(String input) {
        String lowerCase = input.toLowerCase();
        if (isCommand(lowerCase)) { 
            return lowerCase;
        }
        return null;
    }

    public void showAll() {
        for (String c : VALID_COMMANDS) {
            System.out.print(c + "  ");
        }
        System.out.println();
    }
}

