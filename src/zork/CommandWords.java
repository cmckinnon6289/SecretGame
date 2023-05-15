package zork;


public class CommandWords {
    // a constant array that holds all valid command words
    private static final String[] VALID_COMMANDS = {
            "go", "quit", "help", "eat", "get", "run", "grab", "jump", "forward",
            "backward", "left", "right", "up", "down", "drink", "open", "close",
            "look", "search", "strike", "fight", "rest", "heal", "north", "east",
            "west", "south", "exit", "attack", "find", "view", "pick up", "use"
    };

    /**
     * Check whether a given String is a valid command word. Return true if it is,
     * false if it isn't.
     **/
    public static boolean isCommand(String aString) {
        for (String c : VALID_COMMANDS) {
            if (c.equalsIgnoreCase(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /**
     * Returns the canonical version of a command word, resolving synonyms if
     * necessary. Returns null if the input is not a valid command.
     */
    public static String getLowerCaseForm(String input) {
        // First check if the input is already a valid command

        String lowerCase = input.toLowerCase();

        if (isCommand(lowerCase)) {
            return lowerCase;
        }

        // If we get here, the input is not a valid command or synonym
        return null;
    }

    /*
     * Print all valid commands to System.out.
     */
    public static void showAll() {
        for (String c : VALID_COMMANDS) {
            System.out.print(c + "  ");
        }
        System.out.println();
    }
}

