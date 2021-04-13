package interrupt;

import screen.*;

public class Interrupt {

    private final static int MASTER = 0x20;
    private final static int SLAVE = 0xA0;
    private final static int IDT_START = 0x07E00;

    public static void initPic() {
        initIDT();

        // init offset and slave config of master
        programmChip(MASTER, 0x20, 0x04);

        // init offset and slave config of slave
        programmChip(SLAVE, 0x28, 0x02);
    }

    private static void initIDT() {
        /*
        Relevant Exceptions and Interrupts (those reserved are marked with a dash '-'):

        hex   | oirigin   | description              | param
        ------+-----------+--------------------------+------------------
        00    | CPU       | DIV Err                  |
        01    | CPU       | Debug Exception          |
        02    | CPU/Board | NMI                      |
        03    | Code      | Breakpoint               |
        04    | Code      | INTO (Overflow)          |
        05    | Code      | Index Out Of Range       |
        06    | Code      | Invalid Opcode           |
        07    | -         | -                        |
        08    | CPU       | Double Fault             | constant 0
        09-0C | -         | -                        |
        0D    | CPU/Code  | General Protection Error | context dependent
        0E    | CPU       | Page Fault               |
        0F-1F | -         | -                        |
        20    | IRQ0      | Timer                    |
        21    | IRQ1      | Keyboard                 |
        22-2F | IRQ2-IRQ15| Other devices            |
        */
        // pre-initialize everything to be unhandled
        for(int i = 0; i <= 0x2F; i++) {
            registerInterrupt(i, MAGIC.mthdOff("Handler", "unhandled"));
        }
        
        registerInterrupt(0x00, MAGIC.mthdOff("Handler", "divByZero"));
        registerInterrupt(0x01, MAGIC.mthdOff("Handler", "debug"));
        registerInterrupt(0x02, MAGIC.mthdOff("Handler", "nmi"));
        registerInterrupt(0x03, MAGIC.mthdOff("Handler", "breakpoint"));
        registerInterrupt(0x04, MAGIC.mthdOff("Handler", "intoOverflow"));
        registerInterrupt(0x05, MAGIC.mthdOff("Handler", "indexOutOfRange"));
        registerInterrupt(0x06, MAGIC.mthdOff("Handler", "invalidOpcode"));
        registerInterrupt(0x08, MAGIC.mthdOff("Handler", "doubleFault"));
        registerInterrupt(0x0D, MAGIC.mthdOff("Handler", "generalProtectionError"));
        registerInterrupt(0x0E, MAGIC.mthdOff("Handler", "pageFault"));
        registerInterrupt(0x20, MAGIC.mthdOff("Handler", "timer"));
        registerInterrupt(0x21, MAGIC.mthdOff("Handler", "keyboard"));
        for(int i = 0x22; i <= 0x2F; i++) {
            registerInterrupt(i, MAGIC.mthdOff("Handler", "otherDevices"));
        }

        // load Interrupt-Descriptor-Table-Register
        long tmp = (IDT_START << 16) | (long) (48 << 3);
        // lidt [ebp-0x08/tmp]
        MAGIC.inline(0x0F, 0x01, 0x5D);
        MAGIC.inlineOffset(1, tmp);

        // "Set Interrupt Flag", enable interrupts
        MAGIC.inline(0xFB);
    }

    /**
     * mthdOff should be the offset to the handler method from calling the following:
     * MAGIC.mthdOff("Interrupt", "method")
     */
    public static void registerInterrupt(int interrupt, int mthdOff) {
        /*
        Layout
        P   = Present (is page present, 0 = no, 1 = yes, should be 1)
        DPL = Desc. Priv. Level (0 = OS, ..., 3 = User, should be 0)
        D   = Gate Size (offset width, 0 = 16 Bit, 1 = 32 Bit, should be 1)

        63        48 | 47 | 46  45 | 44 | 43 | 42   40 | 39  37 | 36  32 | 31            16 | 15         0
        Offset 31:16 | P  | DPL    | 0  | D  | 1  1  0 | 0 0 0  | Res    | Segment Selector | Offset 15:00
        */
        // this throws a "constant too big for int" error
        //                              48  32  16   0
        // long descriptor = (long) 0x000000088E000000;
        // descriptor |= (mthdAddress & 0x0000FFFF) <<< 46;
        // descriptor |= (mthdAddress & 0xFFFF0000) >>> 16;
        // MAGIC.wMem64(IDT_START + interrupt * 8, descriptor);
        int mthdAddress = MAGIC.rMem32(MAGIC.cast2Ref(MAGIC.clssDesc("Handler")) + mthdOff) + MAGIC.getCodeOff();

        MAGIC.wMem16(IDT_START + interrupt * 8 + 0, (short) (mthdAddress & 0x0000FFFF));
        MAGIC.wMem16(IDT_START + interrupt * 8 + 2, (short) 0x0008);
        MAGIC.wMem16(IDT_START + interrupt * 8 + 4, (short) 0x8E00);
        MAGIC.wMem16(IDT_START + interrupt * 8 + 6, (short) ((mthdAddress & 0xFFFF0000) >>> 16));
    }

    public static void lidtRM() {
        int tableLimit = 8 * 48; // Byte count in table
        long tmp = (long) IDT_START | (long) tableLimit; // 0 is the table base address
        MAGIC.inline(0x0F, 0x01, 0x5D);
        MAGIC.inlineOffset(1, tmp); // lidt [ebp-0x08/tmp]
    }

    private static void programmChip(int port, int offset, int icw3) {
        MAGIC.wIOs8(port++, (byte)0x11);
        MAGIC.wIOs8(port, (byte)offset);
        MAGIC.wIOs8(port, (byte)icw3);
        MAGIC.wIOs8(port, (byte)0x01);
    }
}
