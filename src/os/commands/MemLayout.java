package os.commands;

import os.screen.Color;
import os.screen.Terminal;
import os.utils.stringTemplate.StringTemplate;

public class MemLayout {

    private static class MemoryMap {
        long baseAddress, length;
        int type;

        public MemoryMap(long baseAddress, long length, int type) {
            this.baseAddress = baseAddress;
            this.length = length;
            this.type = type;
        }
    }

    public static MemoryMap getSystemMemoryMap(int continuationIndex) {
        int memStart = 0x8000;
        BIOS.regs.EAX = 0x0000E820;
        BIOS.regs.EDX = 0x534D4150;
        BIOS.regs.EBX = continuationIndex;
        BIOS.regs.ECX = 20;
        BIOS.regs.EDI = memStart;

        BIOS.rint(0x15);


        MemoryMap map = new MemoryMap(
                MAGIC.rMem64(memStart),
                MAGIC.rMem64(memStart + 8),
                MAGIC.rMem32(memStart + 16)
        );

        return map;
    }

    // https://wiki.osdev.org/Detecting_Memory_(x86)#Getting_an_E820_Memory_Map
    public static void printMemLayout(Terminal out) {
        int storeAt = 0x8000;
        BIOS.MemGenerator generator;
        BIOS.MemorySegment segment;

        int c1, c2;
        boolean even;
        int currentColor = out.getColor();

        StringTemplate templ = new StringTemplate("{18cx} | {18cx} | {20r}\n");
        out.setColor(Color.BLACK, Color.GRAY);

        templ.start(out);
        templ.p("Base Address").p("Length").p("Type");

        c1 = Color.mix(Color.GRAY | Color.BRIGHT, Color.BLUE);
        c2 = Color.mix(Color.GRAY | Color.BRIGHT, Color.BLUE | Color.BRIGHT);
        even = true;

        generator = new BIOS.MemGenerator(storeAt);
        segment = generator.next();

        while(segment != null) {
            if(even) out.setColor(c1);
            else out.setColor(c2);
            even = !even;

            templ.start(out);
            templ.p(segment.baseAddress).p(segment.length);

            switch(segment.type) {
                case 1:
                    templ.p("(1) Free Memory");
                    break;

                case 2:
                    templ.p("(2) Reserved Memory");
                    break;

                case 3:
                    templ.p("(3) ACPI reclaimable");
                    break;

                case 4:
                    templ.p("(4) ACPI NVS memory");
                    break;

                case 5:
                    templ.p("(-) Bad memory");
                    break;

                default:
                    templ.p("Unknown");
            }

            segment = generator.next();
        }
        out.setColor(currentColor);
    }

}
