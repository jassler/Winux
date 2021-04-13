package interrupt;

import screen.*;

public class Handler {

    // 0x00
    @SJC.Interrupt
    public static void divByZero() {
        Cursor.staticCursor.println("Div by 0");
    }

    // 0x01
    @SJC.Interrupt
    public static void debug() {
        Cursor.staticCursor.println("Debug Exception");
    }

    // 0x02
    @SJC.Interrupt
    public static void nmi() {
        Cursor.staticCursor.println("NMI Exception");
    }

    // 0x03
    @SJC.Interrupt
    public static void breakpoint() {
        Cursor.staticCursor.println("Breakpoint Exception");
    }

    // 0x04
    @SJC.Interrupt
    public static void intoOverflow() {
        Cursor.staticCursor.println("INTO Overflow");
    }

    // 0x05
    @SJC.Interrupt
    public static void indexOutOfRange() {
        Cursor.staticCursor.println("Index Out Of Range");
    }

    // 0x06
    @SJC.Interrupt
    public static void invalidOpcode() {
        Cursor.staticCursor.println("Invalid Opcode");
    }

    // 0x08
    @SJC.Interrupt
    public static void doubleFault() {
        Cursor.staticCursor.println("Double fault");
    }

    // 0x0D
    @SJC.Interrupt
    public static void generalProtectionError() {
        Cursor.staticCursor.println("General Protection Error");
    }

    // 0x0E
    @SJC.Interrupt
    public static void pageFault() {
        Cursor.staticCursor.println("Page Fault");
    }

    // 0x20
    @SJC.Interrupt
    public static void timer() {
        Cursor.staticCursor.println("Timer");
    }

    // 0x21
    @SJC.Interrupt
    public static void keyboard() {
        Cursor.staticCursor.println("Keyboard");
    }

    // 0x22 - ...
    @SJC.Interrupt
    public static void otherDevices() {
        Cursor.staticCursor.println("Other Devices");
    }
    
    @SJC.Interrupt
    public static void unhandled() {
        Cursor.staticCursor.println("Unhandled");
    }
}