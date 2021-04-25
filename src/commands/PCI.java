package commands;

import kernel.Kernel;
import screen.Terminal;

public class PCI {

    // write into address register...
    public static final int PCI_ADDRESS = 0x0CF8;

    // ...immediately receive data
    public static final int PCI_DATA = 0x0CFC;

    public static PCIInfo info;
    static {
        info = (PCIInfo) MAGIC.cast2Struct(PCI_DATA);
    }

    /**
     * Write to PCI address
     *
     *    31          | 30        24 | 23     16 | 15      11 | 10    8 | 7             2 | 1                     0
     * Enable bit (1) | Reserved (0) |   Bus #   |  Device #  |  Fct #  | Register Offset | Offset cont. (always 0)
     *
     * @param busAddress 0-255: bus address
     * @param devNumber 0-31: device address on bus
     * @param funcAddress 0-7: function address of a device
     * @param register 0-63: register address of function
     */
    @SJC.Inline
    public static void writeAddress(int busAddress, int devNumber, int funcAddress, int register) {
        MAGIC.wIOs32(
                PCI_ADDRESS,
                0x80000000 | (busAddress << 16) | (devNumber << 11) | (funcAddress << 8) | (register << 2)
        );
    }



    /**
     * Prints the following information for each PCI-Device
     *
     * Bus number
     * Device number
     * Function number
     * Basis class code (device category)
     * Sub class code
     * vendor id and device id
     *
     */
    public static void printPCI(Terminal out) {
        out.println("Listing devices");
        all:
        for(int bus = 0; bus < 256; bus++) {
            for(int device = 0; device < 32; device++) {
                for(int function = 0; function < 8; function++) {
                    //writeAddress(bus, device, function, 0);
                    MAGIC.wIOs32(
                            0xCF8,
                            0x80000000 | (bus << 16) | (device << 11) | (function << 8) | (0 << 2)
                    );
                    int result = MAGIC.rIOs32(0x0CFC);
                    if(result != 0) {
                        out.printHex(result);
                        out.println();
                        break all;
                    }
                }
                continue;
                // if -1 or 0, then this device does not exist
                if(info.vendorID == (short) 0xFFFF)
                    continue;

//                out.print("bus: ");
//                out.print(bus);
//                out.print(", dev number: ");
//                out.print(device);
//                out.print(", fct #: ");
//                out.print(0);
//                out.print(", basis class: ");
//                out.printHex(info.basisClassCode);
//                out.print(", sub class: ");
//                out.printHex(info.subClasCode);
//                out.print(", vendor id: ");
//                out.printHex(info.deviceID);
//                out.print(", dev id: ");
//                out.printHex(info.deviceID);
//                out.println();
//                Kernel.sleep(5);
                if(device == 3)
                    break all;
            }
        }
    }

    public static class PCIInfo extends STRUCT {
        short deviceID, vendorID, status, command;
        byte basisClassCode, subClasCode;
    }
}
