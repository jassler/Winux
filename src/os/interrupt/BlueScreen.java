package os.interrupt;

import os.screen.Color;
import os.screen.Cursor;
import os.screen.Terminal;

public class BlueScreen {

    private static final int EIP_OFFSET = 9*4;
    private static final int STACK_BEGINNING = 0x9BFFC;

    public static void show() {
        int i;
        // 0x00b3 <=> tableV / vertical line
        Cursor c = Cursor.staticCursor;

        c.setColor(Color.BLACK, Color.BLUE | Color.BRIGHT);
        c.setCursor(0, 0);
        for(i = 0; i < Terminal.COLS * Terminal.ROWS; i++) {
            c.print(' ');
        }
        c.println("\u00b3     EBP    \u00b3    EBI     \u00b3");

        do {
            c.print("\u00b3 0x");
            c.printHex(StackTraverser.getEbp());

            c.print(" \u00b3 0x");
            c.printHex(StackTraverser.getEip());
            c.print(" \u00b3\n");

        } while(StackTraverser.next());
        //c.println("└────────────┴────────────┘");

    }

}
