package kernel;

import os.commands.*;
import os.interrupt.Handler;
import os.interrupt.Interrupt;
import os.screen.GraphicLogo;
import os.screen.Terminal;
import rte.DynamicRuntime;

public class Kernel {
    public static void main() {
        MAGIC.doStaticInit();
        Interrupt.initPic();

        Terminal mainTerminal = new Terminal();
        mainTerminal.enableCursor();
        mainTerminal.clear();
        mainTerminal.focus();

        BIOS.switchMode(BIOS.GRAPHICS_MODE);

        GraphicLogo.doIntro(1);
        sleep(30);

        BIOS.switchMode(BIOS.TEXT_MODE);

        while (true) {
            String cmd = mainTerminal.promptCommand(256);

            if (cmd.startsWith("echo ")) {
                Echo.handle(cmd, mainTerminal);

            } else if (cmd.startsWith("mem")) {
                MemLayout.printMemLayout(mainTerminal);

            } else if (cmd.startsWith("pci")) {
                PCI.printPCI(mainTerminal);

            } else if (cmd.startsWith("cc")) {
                CC.causeCC();

            } else if (cmd.startsWith("empties")) {
                PrintEmptyObject.printEmptyObject(mainTerminal);

            } else if (cmd.startsWith("help") || cmd.startsWith("?")) {
                mainTerminal.println("echo [value]    - Print value");
                mainTerminal.println("mem             - View BIOS memory usage");
                mainTerminal.println("pci             - View connected PCI devices");
                mainTerminal.println("cc              - Cause Breakboint interrupt");
                mainTerminal.println("help            - View help");
            }
        }
    }

    public static void sleep(int seconds) {
        // https://wiki.osdev.org/PIT
        // https://www.visualmicro.com/page/Timer-Interrupts-Explained.aspx

        Handler.time = 0;
        while (Handler.time < seconds) ;

//    MAGIC.wIOs8(0x70, (byte) 0);
//    byte seconds = (byte) (MAGIC.rIOs8(0x71) & 0xFF);
//    byte ts = seconds;
//    while(seconds + 20 > ts){
//      MAGIC.wIOs8(0x70, (byte) 0);
//      ts = (byte) (MAGIC.rIOs8(0x71) & 0xFF);
//    }
    }
}
