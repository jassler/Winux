package screen;

import keyboard.ASCII;
import keyboard.KeyEvent;
import keyboard.KeyboardController;

public class Terminal {
    public static final int COLS = 80;
    public static final int ROWS = 25;

    public static final int MEM_START = 0xB8000;
    public static final int MEM_END = MEM_START + (COLS * ROWS * 2);

    public static Terminal focused = null;
    public static TerminalMemory mem;
    static {
        mem = (TerminalMemory) MAGIC.cast2Struct(MEM_START);
    }

    private int[][] content;
    private int x, y;

    private int color;

    // custom terminal size in case we might introduce windows or something
    private int tCols, tRows;
    private int xOffset, yOffset;

    private boolean cursorEnabled;

    public Terminal() {
        content = new int[Terminal.ROWS][Terminal.COLS];

        x = y = 0;
        color = Color.mix(Color.GRAY, Color.BLACK);

        xOffset = yOffset = 0;
        tCols = Terminal.COLS;
        tRows = Terminal.ROWS;

        cursorEnabled = false;
        disableCursor();
    }

    @SJC.Inline
    private int calculateMemValue(char c) {
        return (color << 8) | c;
    }

    public void clear() {
        int i;
        for(i = MEM_START; i < MEM_END; i++) {
            MAGIC.wMem8(i, (byte) 0);
        }
    }

    public String promptCommand(int maxLength) {
        print("> ");

        char[] result = new char[maxLength];
        int i = 0;

        while(true) {
            if(!KeyboardController.getKeyBuffer().isEmpty()) {
                KeyEvent k = KeyboardController.getKeyBuffer().pop();

                if(ASCII.isPrintable(k.code)) {

                    if(k.code == ASCII.NEW_LINE) {
                        // done, convert to string
                        print((char) k.code);
                        char[] copied = new char[i];
                        for(int j = 0; j < i; j++) {
                            copied[j] = result[j];
                        }
                        return new String(copied);

                    } else if(i < maxLength) {
                        print((char) k.code);
                        result[i++] = (char) k.code;
                    }

                } else if(k.code == ASCII.BACKSPACE && i > 0) {
                    --i;
                    if(result[i] == ASCII.TAB) {
                        moveCursorHorizontally(-4);
                    } else {
                        moveCursorHorizontally(-1);
                        content[y][x] = 0;

                        if(focused == this) {
                            int index = ((y + yOffset) * Terminal.COLS) + (x + xOffset);
                            if(0 <= index && index < (Terminal.COLS * Terminal.ROWS))
                                mem.screen[index] = (short) 0;
                        }
                    }
                }
            }
        }
    }

    public void focus() {
        Terminal.focused = this;
        int ptr = 0;
        for (int y = 0; y < tRows; y++) {
            for (int x = 0; x < tCols; x++) {
                mem.screen[ptr++] = (byte) content[y][x];
            }
        }
    }

    public void moveCursorHorizontally(int amount) {
        // todo optimize
        if(amount < 0) {
            while(amount++ != 0) {
                if(--x < 0) {
                    x = tCols - 1;
                    if(--y < 0)
                        y = tRows - 1;
                }
            }
        } else if(amount > 0) {
            while(amount-- != 0) {
                if(++x >= tCols) {
                    x = 0;
                    if(++y >= tRows) {
                        y = 0;
                    }
                }
            }
        }
    }

    /*
     * ------- BLINKING CURSOR SECTION ----------
     */
    public void enableCursor() {
        MAGIC.wMem8(0x03D4, (byte) 0x0A);
        int curStart = MAGIC.rMem8(0x3D5) & 0x1F; // get cursor scanline start

        MAGIC.wMem8(0x3D4, (byte) 0x0A);
        MAGIC.wMem8(0x3D5, (byte) (curStart | 0x20)); // set enable bit
    }

    public  void disableCursor() {
        MAGIC.wMem8(0x3D4, (byte) 0x0A);
        MAGIC.wMem8(0x3D5, (byte) 0x20);
    }

    public void moveCursor(int x, int y) {
        int pos = x + (y * Terminal.COLS);

        MAGIC.wMem8(0x3D4, (byte) 0x0F);
        MAGIC.wMem8(0x3D5, (byte) (pos & 0xFF));
        MAGIC.wMem8(0x3D4, (byte) 0x0E);
        MAGIC.wMem8(0x3D5, (byte) ((pos >> 8) & 0xFF));
    }

    public int getCursorPosition() {
        int pos = 0;
        MAGIC.wMem8(0x3D4, (byte) 0x0F);
        pos |= MAGIC.rMem8(0x3D5);
        MAGIC.wMem8(0x3D4, (byte) 0x0E);
        pos |= ((int)MAGIC.rMem8(0x3D5)) << 8;
        return pos;
    }
    /*
     * -------- /BLINKING CURSOR SECTION DONE/ ---------
     */


    public void setColor(int fg, int bg) {
        if(fg < 0 || bg < 0 || fg > 7 || bg > 7)
            return;

        // Layout
        // Bit  7 | 6 5 4 | 3 | 2 1 0
        // Fct  * |  bg   | * |  fg
        color = Color.mix(fg, bg);
    }

    public void setColor(int c) {
        this.color = c;
    }

    public int getColor() {
        return this.color;
    }

    public void setPos(int newX, int newY) {
        if((0 <= newX && newX <= tCols) && (0 <= newY && newY <= tRows)) {
            x = newX;
            y = newY;
            moveCursor(newX, newY);
        }
    }

    public void writeTo(int tmpX, int tmpY, String s) {
        int xOld = x;
        int yOld = y;

        setPos(tmpX, tmpY);
        print(s);
        setPos(xOld, yOld);
    }

    // print overloads
    // only chars are actually written to memory, thus only the char-method does boundary-checks
    public void print(char c) {

        if(c == '\n') {
            x = 0;
            if(++y >= tCols)
                y = 0;
            return;
        } else if(c == '\t') {
            print(' ');
            print(' ');
            print(' ');
            print(' ');
            return;
        }

        int value = calculateMemValue(c);
        content[y][x] = value;

        if(focused == this) {
            int index = ((y + yOffset) * Terminal.COLS) + (x + xOffset);
            if(0 <= index && index < (Terminal.COLS * Terminal.ROWS))
                mem.screen[index] = (short) value;
        }

        if(++x >= tCols) {
            x = 0;
            if(++y >= tRows)
                y = 0;
        }
    }


    public void print(int x) {
        int reverse, digits;

        if(x < 0) {
            print('-');
            x = -x;
        }

        reverse = 0;
        digits = 0;
        do {
            reverse = (reverse * 10) + (x % 10);
            x /= 10;
            digits++;
        } while(x > 0);

        while(digits-- > 0) {
            print((char) (reverse % 10 + '0'));
            reverse /= 10;
        }
    }


    public void printHex(byte b) {
        String hexChars = "0123456789ABCDEF";
        print(hexChars.charAt((b >>> 4) & 0x0F));
        print(hexChars.charAt(b & 0x0F));
    }


    public void printHex(short s) {
        printHex((byte) (s >>> 8));
        printHex((byte) (s & 0xFF));
    }


    public void printHex(int x) {
        printHex((short) (x >>> 16));
        printHex((short) (x & 0xFFFF));
    }


    public void printHex(long x) {
        printHex((int) (x >>> 32));
        printHex((int) (x & 0xFFFFFFFF));
    }


    public void print(long x) {
        long reverse, digits;

        if(x < 0) {
            print('-');
            x = -x;
        }

        reverse = 0;
        digits = 0;
        do {
            reverse = (reverse * 10) + (x % 10);
            x /= 10;
            digits++;
        } while(x > 0);

        while(digits-- > 0) {
            print((char) (reverse % 10 + '0'));
            reverse /= 10;
        }
    }


    public void print(String str) {
        int i;
        for (i=0; i<str.length(); i++) print(str.charAt(i));
    }

    // println overloads
    public void println() {
        print('\n');
    }

    public void println(char c) {
        print(c);
        println();
    }

    public void println(int i) {
        print(i);
        println();
    }

    public void println(long l) {
        print(l);
        println();
    }

    public void println(String str) {
        print(str);
        println();
    }

    private static class TerminalMemory extends STRUCT {
        @SJC(count = Terminal.COLS * Terminal.ROWS)
        private short[] screen;
    }
}
