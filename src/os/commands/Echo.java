package os.commands;

import os.screen.Terminal;

public class Echo {

    public static void handle(String str, Terminal outputTerminal) {
        int i;

        // start after "echo "
        for(i = 5; i < str.length(); i++) {
            outputTerminal.print(str.charAt(i));
        }

        outputTerminal.println();
    }

}
