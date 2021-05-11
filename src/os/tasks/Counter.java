package os.tasks;

import os.interrupt.Handler;
import os.screen.Color;
import os.screen.Cursor;
import os.screen.Terminal;
import os.utils.stringTemplate.NumberWidths;


public class Counter extends Task {
    Cursor c = new Cursor();

    public Counter(String name) {
        super(name);
        c.setColor(Color.GRAY, Color.BLACK);
    }

    @Override
    public void run() {
        int time = Handler.time;
        int width = NumberWidths.getIntWidth(time);

        c.setCursor(Terminal.COLS - width - 1, Terminal.ROWS - 1);
        c.print(time);
    }
}
