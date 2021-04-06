package screen;

public class Cursor {

    private int pos = Screen.MEM_START;
    private byte color = 0x70;
    
    // setColor and setCursor
    public void setColor(int fg, int bg) {
        if(fg < 0 || bg < 0 || fg > 7 || bg > 7)
            return;
        
        // Layout
        // Bit  7 | 6 5 4 | 3 | 2 1 0
        // Fct  * |  bg   | * |  fg
        color = (byte) ((bg << 4) | fg);
    }
    

    public void setCursor(int newX, int newY) {
        if(newX < 0 || newY < 0 || newX >= Screen.WIDTH || newY >= Screen.HEIGHT) {
            this.pos = Screen.MEM_START;
        } else {
            this.pos = Screen.MEM_START + ((newY * Screen.WIDTH * 2) + (newX * 2));
        }
    }
    
    // print overloads
    // only chars are actually written to memory, thus only the char-method does boundary-checks
    public void print(char c) {

        if(c == '\n') {

            // crazy arithmetic that 50% of the time works every time
            // maaaaybe switch to x-y-coordinates instead of juggling with memory addresses
            if(pos >= Screen.MEM_END - (Screen.WIDTH * 2)) {
                // wrap to beginning
                pos = Screen.MEM_START;
            } else {
                // add remaining space of the line to position
                pos += (Screen.WIDTH * 2) - ((pos - Screen.MEM_START) % (Screen.WIDTH * 2));
            }
            return;
        }

        MAGIC.wMem8(pos++, (byte)c);
        MAGIC.wMem8(pos++, color);

        // loop back to beginning?
        if(pos >= Screen.MEM_END) {
            pos = Screen.MEM_START;
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

    // debugging prints
    public static void directPrintInt(int value, int base, int len, int x, int y, int col) {
        Cursor cursor = new Cursor();
        cursor.setCursor(x, y);
        cursor.color = (byte) col;

        if(base == 10)
            cursor.print(value);
        else if(base == 16)
            cursor.printHex(value);
    }
    
    public static void directPrintChar(char c, int x, int y, int col) {
        Cursor cursor = new Cursor();
        cursor.setCursor(x, y);
        cursor.color = (byte) col;
        cursor.print(c);
    }
    
    public static void directPrintString(String s, int x, int y, int col) {
        Cursor cursor = new Cursor();
        cursor.setCursor(x, y);
        cursor.color = (byte) col;
        cursor.print(s);
    }
    
}
