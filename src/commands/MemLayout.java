package commands;

import kernel.Kernel;
import screen.Terminal;

public class MemLayout {

    // https://wiki.osdev.org/Detecting_Memory_(x86)#Getting_an_E820_Memory_Map
    public static void printMemLayout(Terminal out) {
        int storeAt = 0x7E00;

        out.println("Base Address       | Length             | Type");

        BIOS.regs.EBX = 0;
        while(true) {
            BIOS.regs.EAX = 0x0000E820;
            BIOS.regs.EDX = 0x534D4150; // SMAP

            // store result in
            BIOS.regs.EDI = storeAt * 16;
            // buffer size >= 20 bytes
            BIOS.regs.ECX = 24;

            BIOS.rint(0x15);

            // is done?
            if(BIOS.regs.EAX != 0x534D4150 || BIOS.regs.EBX == 0)
                break;

            // error?
            if((BIOS.regs.FLAGS & BIOS.F_CARRY) != 0) {
                out.print("Error: Carry flag was set");
                break;
            }

            long baseAddress = MAGIC.rMem64(storeAt * 16);
            long length = MAGIC.rMem64(storeAt * 16 + 8);
            int type = MAGIC.rMem32(storeAt * 16 + 16);
            int acpi = MAGIC.rMem32(storeAt * 16 + 20);

            out.print("0x");
            out.printHex(baseAddress);

            out.print(" | 0x");
            out.printHex(length);

            out.print(" | (");
            out.print(type);
            out.print(") ");
            switch(type) {
                case 1:
                    out.print("Free Memory");
                    break;

                case 2:
                    out.print("Reserved Memory");
                    break;

                case 3:
                    out.print("ACPI reclaimable");
                    break;

                case 4:
                    out.print("ACPI NVS memory");
                    break;

                case 5:
                    out.print("Area containing bad memory");
                    break;

                default:
                    out.print("Unknown");
            }

            out.print(", ACPI = ");
            out.println(acpi);

            Kernel.sleep(1);
        }

    }

}
